package com.myshop.member.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myshop.FixedDomainFactory;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.query.dto.ChangePasswordRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChangePasswordServiceTest {

	@Autowired
	private ChangePasswordService service;

	@Autowired
	private MemberRepository memberRepository;
	private String memberId;

	@BeforeEach
	void setUp() {
		memberId = "member-1";
		Member member = FixedDomainFactory.createMember(memberId);
		memberRepository.save(member);
	}

	@Test
	void shouldChangePassword() {
		String oldPassword = "12345";
		String newPassword = "newPassword";
		service.changePassword(new ChangePasswordRequest(memberId, oldPassword, newPassword));

		Member findMember = memberRepository.findById(new MemberId(memberId));
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Assertions.assertThat(passwordEncoder.matches(newPassword, findMember.getPassword().getValue())).isTrue();
	}

}
