package com.myshop.order;

public class OrderNotFoundException extends RuntimeException {
	public OrderNotFoundException(OrderNumber number) {
		super("Order not found: " + number);
	}
}
