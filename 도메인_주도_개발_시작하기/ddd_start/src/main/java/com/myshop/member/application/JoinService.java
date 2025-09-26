package com.myshop.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberGrade;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberIdGenerator;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.domain.Password;
import com.myshop.member.error.DuplicateIdException;
import com.myshop.member.query.dto.JoinRequest;
import com.myshop.order.command.domain.model.Address;

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
		new JoinRequestValidator().validate(joinRequest);

		String id = idGenerator.generate().getId();
		String name = joinRequest.getName();
		String address1 = joinRequest.getAddress1();
		String address2 = joinRequest.getAddress2();
		String zipCode = joinRequest.getZipCode();
		String rawPassword = joinRequest.getPassword();
		String email = joinRequest.getEmail();

		// 논리적 오류 검사
		checkDuplicateId(id);

		MemberId memberId = new MemberId(id);
		Address address = new Address(address1, address2, zipCode);
		Password password = new Password(passwordEncoder.encode(rawPassword));
		Member member = new Member(memberId, name, address, password, MemberGrade.basic());
		member.addEmail(email);

		repository.save(member);

		return memberId;
	}

	private void checkDuplicateId(String id) {
		int count = repository.countsById(new MemberId(id));
		if (count > 0) {
			throw new DuplicateIdException(id);
		}
	}
}
