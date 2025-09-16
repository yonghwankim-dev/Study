package com.myshop.coupon.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;
import com.myshop.common.model.Money;

class CouponTest {

	@Test
	void canCreated() {
		Coupon coupon = new Coupon(0.1);

		Assertions.assertThat(coupon).isNotNull();
	}

	@Test
	void applyDiscount() {
		Coupon coupon = new Coupon(0.1);

		Money money = coupon.applyDiscount(FixedDomainFactory.createOrderLine("product-1", 1000, 2));

		Assertions.assertThat(money).isEqualTo(new Money(200));
	}
}
