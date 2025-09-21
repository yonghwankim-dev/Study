package com.myshop.order.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myshop.common.model.Money;
import com.myshop.member.domain.MemberId;
import com.myshop.order.domain.model.OrderLine;
import com.myshop.order.domain.service.DiscountCalculationService;

public class RuleBasedDiscountCalculationService implements DiscountCalculationService {

	private final Map<MemberId, String> discountRules = new HashMap<>();

	public void addDiscountRule(MemberId memberId, String rule) {
		discountRules.put(memberId, rule);
	}

	@Override
	public Money calculateDiscountAmount(List<OrderLine> orderLines, MemberId ordererId) {
		String rule = discountRules.get(ordererId);
		if (rule == null) {
			return new Money(0);
		}

		Money totalAmount = orderLines.stream()
			.map(OrderLine::getAmounts)
			.map(Money::new)
			.reduce(new Money(0), Money::add);

		return switch (rule) {
			case "10PERCENT" -> new Money((int)(totalAmount.getValue() * 0.1));
			case "20PERCENT" -> new Money((int)(totalAmount.getValue() * 0.2));
			case "50PERCENT" -> new Money((int)(totalAmount.getValue() * 0.5));
			default -> new Money(0);
		};
	}
}
