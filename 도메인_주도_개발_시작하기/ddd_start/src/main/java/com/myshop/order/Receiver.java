package com.myshop.order;

import java.util.Objects;

public class Receiver {
	private String name;
	private String phoneNumber;

	public Receiver(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Receiver receiver = (Receiver)object;
		return Objects.equals(name, receiver.name) && Objects.equals(phoneNumber, receiver.phoneNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, phoneNumber);
	}
}
