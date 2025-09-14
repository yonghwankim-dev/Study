package com.myshop.order.domain.discount;

import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;
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

	@Test
	void calculateDiscountAmounts_whenOneOrderLineAndNoCoupon() {
		DiscountCalculationService service = new DiscountCalculationService();
		ArrayList<OrderLine> orderLines = createOrderLine();
		ArrayList<Coupon> coupons = new ArrayList<>();
		MemberGrade memberGrade = new MemberGrade("VIP");
		Money money = service.calculateDiscountAmounts(orderLines, coupons, memberGrade);

		Assertions.assertThat(money).isEqualTo(new Money(100));
	}

	private ArrayList<OrderLine> createOrderLine() {
		ArrayList<OrderLine> orderLines = new ArrayList<>();
		orderLines.add(FixedDomainFactory.createOrderLine("product-1", 1000, 1));
		return orderLines;
	}

}
