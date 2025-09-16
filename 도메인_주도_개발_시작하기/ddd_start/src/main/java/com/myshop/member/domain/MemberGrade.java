package com.myshop.member.domain;

import com.myshop.common.model.Money;
import com.myshop.order.domain.OrderLine;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MemberGrade {

	@Column(name = "grade")
	private String gradeName;

	public MemberGrade() {
	}

	public MemberGrade(String gradeName) {
		this.gradeName = gradeName;
	}

	public static MemberGrade vip() {
		return new MemberGrade("VIP");
	}

	public static MemberGrade basic() {
		return new MemberGrade("BASIC");
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
