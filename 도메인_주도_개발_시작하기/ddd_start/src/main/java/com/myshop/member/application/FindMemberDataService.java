package com.myshop.member.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myshop.member.query.dto.MemberData;
import com.myshop.member.query.dto.MemberSearchRequest;

@Service
public class FindMemberDataService {

	public List<MemberData> findMembers(MemberSearchRequest searchRequest) {
		return new ArrayList<>();
	}
}
