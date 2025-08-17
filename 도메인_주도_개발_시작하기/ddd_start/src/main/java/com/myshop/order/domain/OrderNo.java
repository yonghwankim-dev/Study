package com.myshop.order.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderNo implements Serializable {
	@Column(name = "order_no")
	private String id;

	protected OrderNo() {
	}

	public OrderNo(String id) {
		this.id = id;
	}

	public boolean is2ndGeneration() {
		return id.startsWith("N");
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

	@Override
	public String toString() {
		return "OrderNo{" +
			"id='" + id + '\'' +
			'}';
	}
}
