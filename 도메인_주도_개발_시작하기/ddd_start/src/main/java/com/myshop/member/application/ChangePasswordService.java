package com.myshop.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.query.dto.ChangePasswordRequest;

@Service
public class ChangePasswordService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public ChangePasswordService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		Member member = MemberServiceHelper.findExistingMember(memberRepository, changePasswordRequest.getMemberId());
		member.changePassword(changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword(),
			passwordEncoder);
	}
}
