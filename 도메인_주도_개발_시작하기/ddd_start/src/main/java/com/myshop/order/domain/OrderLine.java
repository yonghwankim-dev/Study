package com.myshop.order.domain;

public class OrderLine {
	private ProductId productId;
	private Money price;
	private int quantity;
	private Money amounts;

	public OrderLine(ProductId productId, Money price, int quantity) {
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
		this.amounts = calculateAmounts();
	}

	private Money calculateAmounts() {
		return price.multiply(quantity);
	}

	public int getAmounts() {
		return amounts.getValue();
	}
}
