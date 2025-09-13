package com.myshop.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.member.EmptyPropertyException;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberIdGenerator;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.domain.Password;
import com.myshop.member.query.dto.JoinRequest;
import com.myshop.order.domain.Address;

@Service
public class JoinService {

	private final MemberRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final MemberIdGenerator idGenerator;

	public JoinService(MemberRepository repository, PasswordEncoder passwordEncoder, MemberIdGenerator idGenerator) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.idGenerator = idGenerator;
	}

	@Transactional
	public MemberId join(JoinRequest joinRequest) {
		String id = idGenerator.generate().toString();
		String name = joinRequest.getName();
		String address1 = joinRequest.getAddress1();
		String address2 = joinRequest.getAddress2();
		String zipCode = joinRequest.getZipCode();
		String rawPassword = joinRequest.getPassword();
		String email = joinRequest.getEmail();

		// 값의 형식 검사
		checkEmpty(id, "id");
		checkEmpty(name, "name");
		checkEmpty(address1, "address1");
		checkEmpty(zipCode, "zipCode");
		checkEmpty(rawPassword, "password");
		checkEmpty(email, "email");

		MemberId memberId = new MemberId(id);
		Address address = new Address(address1, address2, zipCode);
		Password password = new Password(passwordEncoder.encode(rawPassword));
		Member member = new Member(memberId, name, address, password);
		member.addEmail(email);

		repository.save(member);

		return memberId;
	}

	private void checkEmpty(String value, String propertyName) {
		if (value == null || value.isEmpty()) {
			throw new EmptyPropertyException(propertyName);
		}
	}
}
