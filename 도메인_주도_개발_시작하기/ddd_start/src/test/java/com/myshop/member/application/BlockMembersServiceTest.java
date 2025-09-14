package com.myshop.member.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.myshop.FixedDomainFactory;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BlockMembersServiceTest {

	@Autowired
	private BlockMembersService service;

	@Autowired
	private MemberRepository memberRepository;
	private String[] memberIds;

	@BeforeEach
	void setUp() {
		memberIds = new String[] {"12345", "67890"};
		for (String memberId : memberIds) {
			Member member = FixedDomainFactory.createMember(memberId);
			memberRepository.save(member);
		}
	}

	@Test
	void shouldBlockMembers() {
		service.blockMembers(memberIds);

		memberRepository.findByIdIn(memberIds).forEach(member -> {
			assert member.isBlocked() : "Member should be blocked";
		});
	}

	@Test
	void shouldNotBlockMember_whenNoAuthentication() {
		String memberId = memberIds[0];
		Throwable throwable = Assertions.catchThrowable(() -> service.block(memberId));

		Assertions.assertThat(throwable)
			.isInstanceOf(AuthenticationCredentialsNotFoundException.class);
	}
}
