package com.myshop.member.domain;

import com.myshop.order.domain.Address;

public class Member {

	private Address address;

	public void changeAddress(Address address) {
		if (address == null) {
			throw new IllegalArgumentException("Address cannot be null");
		}
		this.address = address;
	}
}
