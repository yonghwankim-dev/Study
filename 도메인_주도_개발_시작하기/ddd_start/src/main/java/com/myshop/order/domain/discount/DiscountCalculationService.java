package com.myshop.order.domain.discount;

import java.util.List;

import com.myshop.common.model.Money;
import com.myshop.coupon.domain.Coupon;
import com.myshop.member.domain.MemberGrade;
import com.myshop.order.domain.OrderLine;

public class DiscountCalculationService {

	public Money calculateDiscountAmounts(List<OrderLine> orderLines, List<Coupon> coupons, MemberGrade grade) {
		Money couponDiscount = coupons.stream()
			.map(this::calculateDiscount)
			.reduce(new Money(0),
				(money, money2) -> money != null && money2 != null ? money.add(money2) : new Money(0));

		Money membershipDiscount = calculateDiscount(grade);
		return couponDiscount.add(membershipDiscount);
	}

	private Money calculateDiscount(Coupon coupon) {
		return new Money(0);
	}

	private Money calculateDiscount(MemberGrade grade) {
		return new Money(0);
	}
}
