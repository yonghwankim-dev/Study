package com.myshop.order.application;

public class OrderProduct {
	private String productId;
	private int quantity;

	public OrderProduct() {
	}

	public OrderProduct(String productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}
}
