package com.myshop.member.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myshop.order.domain.Address;

class MemberTest {

	private Member member;

	@BeforeEach
	void setUp() {
		MemberId memberId = new MemberId("12345");
		Address address = new Address(
			"서울특별시 강남구 역삼동",
			"831-7",
			"06242"
		);
		String currentPassword = "password1234";
		Password password = new Password(currentPassword);
		member = new Member(memberId, address, password);
	}

	@Test
	void shouldNotThrow_whenCurrentPasswordMatch(){
		String currentPassword = "password1234";
		String newPassword = "newPassword1234";

		assertDoesNotThrow(()-> member.changePassword(currentPassword, newPassword));
	}

	@Test
	void shouldThrow_whenCurrentPasswordNotMatch() {
		String notMatchingCurrentPassword = "wrongPassword";
		String newPassword = "newPassword1234";

		Throwable throwable = catchThrowable(() -> member.changePassword(notMatchingCurrentPassword, newPassword));

		assertThat(throwable)
			.isInstanceOf(PasswordNotMatchException.class);
	}
}
