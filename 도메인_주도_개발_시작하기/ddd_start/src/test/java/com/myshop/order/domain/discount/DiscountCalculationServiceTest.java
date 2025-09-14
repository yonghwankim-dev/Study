package com.myshop.order.domain.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.common.model.Money;

class DiscountCalculationServiceTest {

	@Test
	void canCreated() {
		DiscountCalculationService service = new DiscountCalculationService();

		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void calculateDiscountAmounts() {
		DiscountCalculationService service = new DiscountCalculationService();

		Money money = service.calculateDiscountAmounts();

		Assertions.assertThat(money).isEqualTo(null);
	}

}
