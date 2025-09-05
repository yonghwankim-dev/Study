package com.myshop.springconfig.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;

public class MemberDetailService implements UserDetailsService {
	private final MemberRepository memberRepository;

	public MemberDetailService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Member member = memberRepository.findById(new MemberId(memberId));

		return User.builder()
			.username(member.getId().getId())
			.password(member.getPassword().getValue())
			.roles("USER")
			.build();
	}
}
