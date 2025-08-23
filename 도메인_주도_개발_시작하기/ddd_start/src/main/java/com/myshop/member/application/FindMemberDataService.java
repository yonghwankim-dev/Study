package com.myshop.member.application;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
		Specification<MemberData> spec = Specification.where(null);
		if (searchRequest.isOnlyNotBlocked()) {
			spec = spec.and(MemberDataSpecs.nonBlocked());
		}
		if (StringUtils.hasText(searchRequest.getName())) {
			spec = spec.and(MemberDataSpecs.nameLike(searchRequest.getName()));
		}

		return memberDataDao.findAll(spec, PageRequest.of(0, 5)).getContent();
	}
}
