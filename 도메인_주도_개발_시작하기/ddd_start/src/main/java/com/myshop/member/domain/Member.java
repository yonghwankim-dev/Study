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
	private MemberId id;

	@Embedded
	private Address address;

	@Embedded
	private Password password;

	protected Member() {
		// Default constructor for JPA
	}

	public Member(MemberId id, Address address) {
		this.id = id;
		this.address = address;
	}

	public void changeAddress(Address address) {
		if (address == null) {
			throw new IllegalArgumentException("Address cannot be null");
		}
		this.address = address;
	}

	public void changePassword(String currentPassword, String newPassword){
		if (!password.match(currentPassword)) {
			throw new PasswordNotMatchException();
		}
		this.password = new Password(newPassword);
	}
}
