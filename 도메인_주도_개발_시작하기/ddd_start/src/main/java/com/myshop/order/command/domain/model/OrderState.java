package com.myshop.order.command.domain.model;

public enum OrderState {
	PAYMENT_WAITING,
	PREPARING,
	SHIPPED,
	DELIVERING,
	DELIVERY_COMPLETED,
	CANCELED
}
