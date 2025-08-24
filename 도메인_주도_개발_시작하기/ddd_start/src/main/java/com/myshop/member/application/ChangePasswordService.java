package com.myshop.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;

@Service
public class ChangePasswordService {

	private final MemberRepository memberRepository;

	public ChangePasswordService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void changePassword(String memberId, String oldPassword, String newPassword) {
		Member member = memberRepository.findById(new MemberId(memberId));
		member.changePassword(oldPassword, newPassword);
	}
}
