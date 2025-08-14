package com.myshop.order;

import com.myshop.order.domain.OrderNumber;

public class NoOrderException extends RuntimeException {
	public NoOrderException(OrderNumber number) {
		super("Order not found: " + number);
	}
}
