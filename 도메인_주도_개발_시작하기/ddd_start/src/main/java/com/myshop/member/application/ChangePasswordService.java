package com.myshop.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.query.dto.ChangePasswordRequest;

@Service
public class ChangePasswordService {

	private final MemberRepository memberRepository;

	public ChangePasswordService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		Member member = MemberServiceHelper.findExistingMember(memberRepository, changePasswordRequest.memberId());
		member.changePassword(changePasswordRequest.oldPassword(), changePasswordRequest.newPassword());
	}
}
