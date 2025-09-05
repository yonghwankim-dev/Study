package com.myshop.member.application;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.domain.Password;
import com.myshop.member.query.dto.JoinRequest;
import com.myshop.order.domain.Address;

@Service
public class JoinService {

	private final MemberRepository repository;
	private final PasswordEncoder passwordEncoder;

	public JoinService(MemberRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public void join(JoinRequest joinRequest) {
		String name = joinRequest.getName();
		Address address = new Address(joinRequest.getAddress1(), joinRequest.getAddress2(), joinRequest.getZipCode());
		Password password = new Password(passwordEncoder.encode(joinRequest.getPassword()));
		MemberId memberId = new MemberId(UUID.randomUUID().toString());
		Member member = new Member(memberId, name, address, password);
		member.addEmail(joinRequest.getEmail());
		repository.save(member);
	}
}
