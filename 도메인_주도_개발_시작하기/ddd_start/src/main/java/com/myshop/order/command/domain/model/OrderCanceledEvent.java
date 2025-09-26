package com.myshop.order.command.domain.model;

import com.myshop.common.event.Event;

public class OrderCanceledEvent extends Event {
	private String orderNumber;

	public OrderCanceledEvent(String orderNumber) {
		super();
		this.orderNumber = orderNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}
}
