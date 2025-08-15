package com.myshop.member.domain;

import com.myshop.order.domain.Address;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

	@EmbeddedId
	private MemberId memberId;

	@Embedded
	private Address address;

	protected Member() {
		// Default constructor for JPA
	}

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
