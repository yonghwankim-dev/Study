package com.myshop.order.domain.discount;

import java.util.List;

import com.myshop.common.model.Money;
import com.myshop.coupon.domain.Coupon;
import com.myshop.member.domain.MemberGrade;
import com.myshop.order.domain.OrderLine;

public class DiscountCalculationService {

	// todo: Coupon 테스트 케이스 추가
	public Money calculateDiscountAmounts(List<OrderLine> orderLines, List<Coupon> coupons, MemberGrade grade) {
		Money couponDiscount = coupons.stream()
			.map(coupon -> calculateDiscount(coupon, orderLines))
			.reduce(new Money(0),
				(money, money2) -> money != null ? money.add(money2) : new Money(0));

		Money membershipDiscount = calculateDiscount(grade, orderLines);
		return couponDiscount.add(membershipDiscount);
	}

	private Money calculateDiscount(Coupon coupon, List<OrderLine> orderLines) {
		return orderLines.stream()
			.map(coupon::applyDiscount)
			.reduce(new Money(0),
				(money, money2) -> money != null ? money.add(money2) : new Money(0));
	}

	private Money calculateDiscount(MemberGrade grade, List<OrderLine> orderLines) {
		return orderLines.stream()
			.map(grade::applyDiscount)
			.reduce(new Money(0),
				(money, money2) -> money != null ? money.add(money2) : new Money(0));
	}
}
