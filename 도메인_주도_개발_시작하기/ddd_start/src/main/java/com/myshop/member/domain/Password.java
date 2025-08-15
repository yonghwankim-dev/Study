package com.myshop.member.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Password {
	@Column(name = "password")
	private String value;

	protected Password() {
		// Default constructor for JPA
	}

	public Password(String value) {
		this.value = value;
	}

	public boolean match(String password) {
		return value.equals(password);
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Password password = (Password)object;
		return Objects.equals(value, password.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
