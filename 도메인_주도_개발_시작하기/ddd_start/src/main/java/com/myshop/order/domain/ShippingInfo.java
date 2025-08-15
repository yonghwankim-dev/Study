package com.myshop.order.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class ShippingInfo {
	@Embedded
	private Receiver receiver;
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "address1", column = @Column(name = "shipping_addr1")),
		@AttributeOverride(name = "address2", column = @Column(name = "shipping_addr2")),
		@AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code"))
	})
	private Address address;

	protected ShippingInfo() {

	}

	public ShippingInfo(Receiver receiver, Address address) {
		this.receiver = receiver;
		this.address = address;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public Address getAddress() {
		return address;
	}
}
