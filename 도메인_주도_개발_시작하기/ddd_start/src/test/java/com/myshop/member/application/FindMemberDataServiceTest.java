package com.myshop.member.application;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.FixedDomainFactory;
import com.myshop.member.query.dao.MemberDataDao;
import com.myshop.member.query.dto.MemberData;
import com.myshop.member.query.dto.MemberSearchRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FindMemberDataServiceTest {
	@Autowired
	private FindMemberDataService service;

	@Autowired
	private MemberDataDao memberDataDao;

	private void saveMemberDates() {
		for (int i = 1; i <= 20; i++) {
			String id = String.format("%05d", i);
			MemberData memberData = FixedDomainFactory.createMemberData(id);
			memberDataDao.save(memberData);
		}
	}

	@BeforeEach
	void setUp() {
		saveMemberDates();
	}

	@AfterEach
	void tearDown() {
		memberDataDao.deleteAll();
	}

	@Test
	void shouldReturnMemberData() {
		MemberSearchRequest searchRequest = new MemberSearchRequest();

		List<MemberData> memberDataList = service.findMembers(searchRequest);

		Assertions.assertThat(memberDataList).hasSize(5);
	}
}
