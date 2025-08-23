package com.myshop.member.application;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myshop.common.jpa.SpecBuilder;
import com.myshop.member.query.dao.MemberDataDao;
import com.myshop.member.query.dao.MemberDataSpecs;
import com.myshop.member.query.dto.MemberData;
import com.myshop.member.query.dto.MemberSearchRequest;

@Service
public class FindMemberDataService {

	private final MemberDataDao memberDataDao;

	public FindMemberDataService(MemberDataDao memberDataDao) {
		this.memberDataDao = memberDataDao;
	}

	public List<MemberData> findMembers(MemberSearchRequest searchRequest) {
		Specification<MemberData> spec = SpecBuilder.builder(MemberData.class)
			.ifTrue(searchRequest.isOnlyNotBlocked(), MemberDataSpecs::nonBlocked)
			.ifHasText(searchRequest.getName(), MemberDataSpecs::nameLike)
			.toSpec();
		return memberDataDao.findAll(spec, PageRequest.of(0, 5)).getContent();
	}
}
