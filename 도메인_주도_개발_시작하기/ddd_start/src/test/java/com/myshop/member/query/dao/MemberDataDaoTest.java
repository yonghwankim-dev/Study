package com.myshop.member.query.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.member.query.dto.MemberData;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberDataDaoTest {

	@Autowired
	private MemberDataDao memberDataDao;

	@AfterEach
	void tearDown() {
		memberDataDao.deleteAll();
	}

	@Test
	void shouldSaveMemberData() {
		String id = String.format("%05d", 1);
		String name = "james";
		boolean blocked = false;
		MemberData memberData = new MemberData(id, name, blocked);

		memberDataDao.save(memberData);

		MemberData findMemberData = memberDataDao.findById(id);
		Assertions.assertThat(findMemberData).isEqualTo(memberData);
	}

}
