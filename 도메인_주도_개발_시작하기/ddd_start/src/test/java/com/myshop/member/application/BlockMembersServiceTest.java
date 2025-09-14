package com.myshop.member.application;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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

		// SecurityContext에 ADMIN 권한을 가진 사용자 등록
		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(
				"admin",           // principal
				"password",        // credentials
				List.of(new SimpleGrantedAuthority("ROLE_ADMIN")) // 권한
			);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@AfterEach
	void tearDown() {
		memberRepository.deleteAll();
		SecurityContextHolder.clearContext();
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
		// 미인증 상태로 초기화
		SecurityContextHolder.clearContext();

		String memberId = memberIds[0];
		Throwable throwable = Assertions.catchThrowable(() -> service.block(memberId));

		Assertions.assertThat(throwable)
			.isInstanceOf(AuthenticationCredentialsNotFoundException.class);
	}

	@Test
	void shouldNotBlockMember_whenNoPermission() {
		// SecurityContext에 USER 권한만 가진 사용자 등록
		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(
				"user",            // principal
				"password",        // credentials
				List.of(new SimpleGrantedAuthority("ROLE_USER")) // 권한
			);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String memberId = memberIds[0];
		Throwable throwable = Assertions.catchThrowable(() -> service.block(memberId));

		Assertions.assertThat(throwable)
			.isInstanceOf(AccessDeniedException.class);
	}
}
