package com.myshop.order.domain.discount;

import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.common.model.Money;
import com.myshop.member.domain.MemberGrade;

class DiscountCalculationServiceTest {

	@Test
	void canCreated() {
		DiscountCalculationService service = new DiscountCalculationService();

		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void calculateDiscountAmounts() {
		DiscountCalculationService service = new DiscountCalculationService();

		Money money = service.calculateDiscountAmounts(new ArrayList<>(), new ArrayList<>(), new MemberGrade());

		Assertions.assertThat(money).isEqualTo(new Money(0));
	}

}
