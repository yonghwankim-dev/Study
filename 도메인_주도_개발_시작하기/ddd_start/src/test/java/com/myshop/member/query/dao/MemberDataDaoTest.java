package com.myshop.member.query.dao;

import java.util.Comparator;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

	private void saveMemberDates() {
		for (int i = 1; i <= 20; i++) {
			String id = String.format("%05d", i);
			MemberData memberData = createMemberData(id);
			memberDataDao.save(memberData);
		}
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

	@Test
	void shouldReturnMemberDataListByNameAndPageable() {
		saveMemberDates();
		String name = "jam%";
		Pageable pageable = PageRequest.of(0, 5);

		List<MemberData> memberDataList = memberDataDao.findByNameLike(name, pageable);

		Assertions.assertThat(memberDataList).hasSize(5);
	}

	@Test
	void shouldReturnMemberDataListByNameAndPageableAndSort() {
		saveMemberDates();
		String name = "jam%";
		Sort sort = Sort.by("name").descending();
		Pageable pageable = PageRequest.of(0, 5, sort);

		List<MemberData> memberDataList = memberDataDao.findByNameLike(name, pageable);

		Assertions.assertThat(memberDataList)
			.hasSize(5)
			.isSortedAccordingTo(Comparator.comparing(MemberData::getName).reversed());
	}

	@Test
	void shouldReturnPageMemberDataByBlockedAndPageable() {
		saveMemberDates();
		boolean blocked = false;
		Pageable pageable = PageRequest.of(0, 5);

		Page<MemberData> page = memberDataDao.findByBlocked(blocked, pageable);

		Assertions.assertThat(page.getContent()).hasSize(5);
		Assertions.assertThat(page.getTotalElements()).isEqualTo(20);
		Assertions.assertThat(page.getTotalPages()).isEqualTo(4);
		Assertions.assertThat(page.getNumber()).isZero();
		Assertions.assertThat(page.getSize()).isEqualTo(5);
	}
}
