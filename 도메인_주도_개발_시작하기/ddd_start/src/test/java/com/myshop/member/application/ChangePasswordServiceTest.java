package com.myshop.member.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.FixedDomainFactory;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.domain.Password;
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
		memberId = "12345";
		Member member = FixedDomainFactory.createMember(memberId);
		memberRepository.save(member);
	}

	@Test
	void shouldChangePassword() {
		service.changePassword(new ChangePasswordRequest(memberId, "12345", "newPassword"));

		Member findMember = memberRepository.findById(new MemberId(memberId));
		Assertions.assertThat(findMember.getPassword()).isEqualTo(new Password("newPassword"));
	}

}
