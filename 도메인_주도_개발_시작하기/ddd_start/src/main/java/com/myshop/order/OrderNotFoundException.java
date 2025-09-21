package com.myshop.order;

import com.myshop.order.domain.model.OrderNo;

public class OrderNotFoundException extends RuntimeException {
	public OrderNotFoundException(OrderNo id) {
		super("Order not found: " + id);
	}
}
