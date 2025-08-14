package com.myshop.order;

public class OrderNotFoundException extends RuntimeException {
	public OrderNotFoundException(String orderId) {
		super("Order not found: " + orderId);
	}
}
