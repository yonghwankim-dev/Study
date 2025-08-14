package com.myshop.order.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderNo implements java.io.Serializable {
	@Column
	private String id;

	protected OrderNo() {
	}

	public OrderNo(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		OrderNo orderNo = (OrderNo)object;
		return Objects.equals(id, orderNo.id);
	}
}
