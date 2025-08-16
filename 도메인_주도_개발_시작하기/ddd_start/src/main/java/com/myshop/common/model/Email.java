package com.myshop.common.model;

import java.util.Objects;

public class Email {
	private String address;

	public Email(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Email email = (Email)object;
		return Objects.equals(address, email.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address);
	}
}
