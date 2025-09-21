package com.myshop.order.domain.model;

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
}
