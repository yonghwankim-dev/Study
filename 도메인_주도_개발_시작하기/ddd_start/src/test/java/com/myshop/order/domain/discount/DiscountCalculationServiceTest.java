package com.myshop.order.domain.discount;

import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.common.model.Money;
import com.myshop.coupon.domain.Coupon;
import com.myshop.member.domain.MemberGrade;
import com.myshop.order.domain.OrderLine;

class DiscountCalculationServiceTest {

	@Test
	void canCreated() {
		DiscountCalculationService service = new DiscountCalculationService();

		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void calculateDiscountAmounts_whenEmptyOrderLine() {
		DiscountCalculationService service = new DiscountCalculationService();

		ArrayList<OrderLine> orderLines = new ArrayList<>();
		ArrayList<Coupon> coupons = new ArrayList<>();
		MemberGrade memberGrade = new MemberGrade("VIP");
		Money money = service.calculateDiscountAmounts(orderLines, coupons, memberGrade);

		Assertions.assertThat(money).isEqualTo(new Money(0));
	}

}
