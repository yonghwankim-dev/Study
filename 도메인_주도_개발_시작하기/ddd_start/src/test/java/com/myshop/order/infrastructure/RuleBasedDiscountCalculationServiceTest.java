package com.myshop.order.infrastructure;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;
import com.myshop.common.model.Money;
import com.myshop.member.domain.MemberId;
import com.myshop.order.command.domain.model.OrderLine;
import com.myshop.order.command.domain.service.DiscountCalculationService;

class RuleBasedDiscountCalculationServiceTest {

	@Test
	void canCreated() {
		DiscountCalculationService discountCalculationService = new RuleBasedDiscountCalculationService();

		Assertions.assertThat(discountCalculationService).isNotNull();
	}

	@Test
	void shouldReturnZero_whenNoDiscountRulesApply() {
		DiscountCalculationService discountCalculationService = new RuleBasedDiscountCalculationService();
		MemberId memberId = new MemberId("member-1");

		Money discountAmount = discountCalculationService.calculateDiscountAmount(null, memberId);

		Assertions.assertThat(discountAmount).isEqualTo(new Money(0));
	}

	@Test
	void shouldReturnDiscountAmount_whenDiscountRulesApply() {
		MemberId memberId = new MemberId("member-1");
		RuleBasedDiscountCalculationService discountCalculationService = new RuleBasedDiscountCalculationService();
		discountCalculationService.addDiscountRule(memberId, "10PERCENT");
		OrderLine orderLine1 = FixedDomainFactory.createOrderLine("product-1", 1000, 1);
		OrderLine orderLine2 = FixedDomainFactory.createOrderLine("product-2", 2000, 2);
		List<OrderLine> orderLines = List.of(orderLine1, orderLine2);

		Money discountAmount = discountCalculationService.calculateDiscountAmount(orderLines, memberId);

		Assertions.assertThat(discountAmount).isEqualTo(new Money(500));
	}
}
