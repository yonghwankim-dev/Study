package com.myshop.order.domain.model;

import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class ShippingInfo {
	@Embedded
	private Receiver receiver;

	@Column(name = "shipping_message")
	private String message;

	@Embedded
	@AttributeOverride(name = "address1", column = @Column(name = "shipping_addr1"))
	@AttributeOverride(name = "address2", column = @Column(name = "shipping_addr2"))
	@AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code"))
	private Address address;

	protected ShippingInfo() {

	}

	public ShippingInfo(Receiver receiver, String message, Address address) {
		this.receiver = receiver;
		this.address = address;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		ShippingInfo that = (ShippingInfo)object;
		return Objects.equals(receiver, that.receiver) && Objects.equals(message, that.message)
			&& Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(receiver, message, address);
	}

	@Override
	public String toString() {
		return "ShippingInfo{" +
			"receiver=" + receiver +
			", message='" + message + '\'' +
			", address=" + address +
			'}';
	}
}
