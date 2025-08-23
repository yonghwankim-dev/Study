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

	private MemberData createMemberData(String id) {
		String name = "james";
		boolean blocked = false;
		return new MemberData(id, name, blocked);
	}

	@AfterEach
	void tearDown() {
		memberDataDao.deleteAll();
	}

	@Test
	void shouldSaveMemberData() {
		String id = String.format("%05d", 1);
		MemberData memberData = createMemberData(id);

		memberDataDao.save(memberData);

		MemberData findMemberData = memberDataDao.findById(id);
		Assertions.assertThat(findMemberData).isEqualTo(memberData);
	}
}
