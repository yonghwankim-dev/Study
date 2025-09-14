package com.myshop.coupon.domain;

import com.myshop.common.model.Money;
import com.myshop.order.domain.OrderLine;

public class Coupon {

	private final double discountRate;

	public Coupon(double discountRate) {
		this.discountRate = discountRate;
	}

	public Money applyDiscount(OrderLine orderLine) {
		int amounts = orderLine.getAmounts();
		return new Money((int)(amounts * discountRate));
	}
}
