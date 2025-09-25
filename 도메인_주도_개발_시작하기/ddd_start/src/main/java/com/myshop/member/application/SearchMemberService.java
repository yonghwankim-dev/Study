package com.myshop.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;

@Service
public class SearchMemberService {
	private final MemberRepository memberRepository;

	public SearchMemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional(readOnly = true)
	public Member search(String memberId) {
		return memberRepository.findById(new MemberId(memberId));
	}
}
