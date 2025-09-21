package com.myshop.order.infrastructure;

import java.util.List;

import com.myshop.common.model.Money;
import com.myshop.order.domain.model.OrderLine;
import com.myshop.order.domain.service.DiscountCalculationService;

public class RuleBasedDiscountCalculationService implements DiscountCalculationService {

	@Override
	public Money calculateDiscountAmount(List<OrderLine> orderLines) {
		return new Money(0);
	}
}
