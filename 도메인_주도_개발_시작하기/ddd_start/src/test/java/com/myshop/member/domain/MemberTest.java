package com.myshop.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.order.domain.Address;

class MemberTest {

	@Test
	void shouldNotThrow_whenCurrentPasswordMatch(){
		MemberId memberId = new MemberId("12345");
		Address address = new Address(
			"서울특별시 강남구 역삼동",
			"831-7",
			"06242"
		);
		String currentPassword = "password1234";
		Password password = new Password(currentPassword);
		Member member = new Member(memberId, address, password);
		String newPassword = "newPassword1234";

		Assertions.assertDoesNotThrow(()-> member.changePassword(currentPassword, newPassword));
	}
}
