package com.myshop.order.domain.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Receiver {
	@Column(name = "receiver_name")
	private String name;
	@Column(name = "receiver_phone")
	private String phone;

	protected Receiver() {

	}

	public Receiver(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Receiver receiver = (Receiver)object;
		return Objects.equals(name, receiver.name) && Objects.equals(phone, receiver.phone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, phone);
	}

	@Override
	public String toString() {
		return "Receiver{" +
			"name='" + name + '\'' +
			", phone='" + phone + '\'' +
			'}';
	}
}
