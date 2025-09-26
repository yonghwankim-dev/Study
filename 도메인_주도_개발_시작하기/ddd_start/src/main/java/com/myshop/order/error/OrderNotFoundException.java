package com.myshop.order.error;

import com.myshop.order.command.domain.model.OrderNo;

public class OrderNotFoundException extends RuntimeException {
	public OrderNotFoundException(OrderNo id) {
		super("Order not found: " + id);
	}
}
