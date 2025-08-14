package com.myshop.order;

public class NoOrderException extends RuntimeException {
	public NoOrderException(OrderNumber number) {
		super("Order not found: " + number);
	}
}
