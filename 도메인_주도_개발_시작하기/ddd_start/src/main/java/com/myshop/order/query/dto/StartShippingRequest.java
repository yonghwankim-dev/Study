package com.myshop.order.query.dto;

public class StartShippingRequest {

	private String orderNumber;
	private long version;

	public StartShippingRequest(String orderNumber, long version) {
		this.orderNumber = orderNumber;
		this.version = version;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public long getVersion() {
		return version;
	}
}
