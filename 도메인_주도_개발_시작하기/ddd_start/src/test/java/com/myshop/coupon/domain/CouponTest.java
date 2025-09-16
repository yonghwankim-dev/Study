package com.myshop.coupon.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CouponTest {

	@Test
	void canCreated() {
		Coupon coupon = new Coupon(0.1);

		Assertions.assertThat(coupon).isNotNull();
	}

}
