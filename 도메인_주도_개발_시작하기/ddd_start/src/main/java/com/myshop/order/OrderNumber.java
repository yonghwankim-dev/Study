package com.myshop.order;

import java.util.Objects;

public record OrderNumber(String id) {

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		OrderNumber orderNumber = (OrderNumber)object;
		return Objects.equals(id, orderNumber.id);
	}
}
