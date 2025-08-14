package com.myshop.order;

import com.myshop.order.domain.OrderNo;

public class NoOrderException extends RuntimeException {
	public NoOrderException(OrderNo id) {
		super("Order not found: " + id);
	}
}
