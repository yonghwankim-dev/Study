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
		List<MemberData> datas = FixedDomainFactory.createFixedMemberDataList();
		datas.forEach(memberDataDao::save);
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
		MemberSearchRequest searchRequest = new MemberSearchRequest(true, "jam");

		List<MemberData> memberDataList = service.findMembers(searchRequest);

		Assertions.assertThat(memberDataList)
			.hasSize(5)
			.allMatch(memberData -> !memberData.isBlocked())
			.allMatch(memberData -> memberData.getName().contains("jam"));
	}
}
