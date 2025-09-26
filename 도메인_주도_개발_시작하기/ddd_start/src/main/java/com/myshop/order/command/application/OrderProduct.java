package com.myshop.order.command.application;

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

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
