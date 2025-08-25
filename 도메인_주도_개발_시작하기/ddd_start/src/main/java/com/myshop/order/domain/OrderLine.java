package com.myshop.order.domain;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.common.model.Money;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class OrderLine {
	@Embedded
	private ProductId productId;
	@Column(name = "price")
	private Money price;
	@Column(name = "quantity")
	private int quantity;

	@Column(name = "amounts")
	private Money amounts;

	protected OrderLine() {
	}

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
