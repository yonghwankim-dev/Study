package com.myshop.order.domain;

import java.util.Objects;

public record OrderNo(String id) {

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
