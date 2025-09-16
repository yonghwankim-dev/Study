package com.myshop.coupon.domain;

import com.myshop.common.model.Money;
import com.myshop.order.domain.OrderLine;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Coupon {

	@Column(name = "discount_rate")
	private double discountRate;

	protected Coupon() {
	}

	public Coupon(double discountRate) {
		this.discountRate = discountRate;
	}

	public Money applyDiscount(OrderLine orderLine) {
		int amounts = orderLine.getAmounts();
		return new Money((int)(amounts * discountRate));
	}
}
