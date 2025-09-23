package com.myshop.springconfig.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.query.dto.MemberAuthentication;

public class MemberDetailService implements UserDetailsService {
	private final MemberRepository memberRepository;

	public MemberDetailService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email).orElseThrow();

		return new MemberAuthentication(
			member.getId().getId(),
			member.getPassword().getValue(),
			List.of(new SimpleGrantedAuthority("USER")),
			member.getId().getId(),
			member.getName()
		);
	}
}
