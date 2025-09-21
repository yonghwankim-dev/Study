package com.myshop.order.infrastructure;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.common.model.Money;
import com.myshop.order.domain.service.DiscountCalculationService;

class RuleBasedDiscountCalculationServiceTest {

	@Test
	void canCreated() {
		DiscountCalculationService discountCalculationService = new RuleBasedDiscountCalculationService();

		Assertions.assertThat(discountCalculationService).isNotNull();
	}

	@Test
	void shouldReturnZero_whenNoDiscountRulesApply() {
		DiscountCalculationService discountCalculationService = new RuleBasedDiscountCalculationService();

		Money discountAmount = discountCalculationService.calculateDiscountAmount(null);

		Assertions.assertThat(discountAmount).isEqualTo(new Money(0));
	}
}
