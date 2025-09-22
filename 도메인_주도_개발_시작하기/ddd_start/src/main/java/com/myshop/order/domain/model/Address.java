package com.myshop.order.domain.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
	@Column(name = "address1")
	private String address1;
	@Column(name = "address2")
	private String address2;
	@Column(name = "zip_code")
	private String zipCode;

	protected Address() {
	}

	public Address(String address1, String address2, String zipCode) {
		this.address1 = address1;
		this.address2 = address2;
		this.zipCode = zipCode;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getZipCode() {
		return zipCode;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Address address = (Address)object;
		return Objects.equals(address1, address.address1) && Objects.equals(address2, address.address2)
			&& Objects.equals(zipCode, address.zipCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address1, address2, zipCode);
	}

	@Override
	public String toString() {
		return "Address{" +
			"address1='" + address1 + '\'' +
			", address2='" + address2 + '\'' +
			", zipCode='" + zipCode + '\'' +
			'}';
	}
}
