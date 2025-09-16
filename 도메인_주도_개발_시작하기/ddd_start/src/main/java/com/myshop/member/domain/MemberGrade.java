package com.myshop.member.domain;

import com.myshop.common.model.Money;
import com.myshop.order.domain.OrderLine;

public class MemberGrade {

	private final String gradeName;

	public MemberGrade(String gradeName) {
		this.gradeName = gradeName;
	}

	public static MemberGrade vip() {
		return new MemberGrade("VIP");
	}

	public Money applyDiscount(OrderLine orderLine) {
		if ("VIP".equals(gradeName)) {
			int amounts = orderLine.getAmounts();
			return new Money((int)(amounts * 0.1));
		} else if ("PREMIUM".equals(gradeName)) {
			int amounts = orderLine.getAmounts();
			return new Money((int)(amounts * 0.05));
		} else {
			return new Money(0);
		}
	}
}
