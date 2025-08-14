package com.myshop.member.domain;

import com.myshop.order.domain.Address;

public class Member {


	private MemberId memberId;
	private Address address;

	public Member(MemberId memberId, Address address) {
		this.memberId = memberId;
		this.address = address;
	}

	public void changeAddress(Address address) {
		if (address == null) {
			throw new IllegalArgumentException("Address cannot be null");
		}
		this.address = address;
	}
}
