package com.myshop.order.infrastructure;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import com.myshop.order.query.dto.OrderSummary;

class OrdererIdSpecTest {

	@Test
	void canCreated() {
		Specification<OrderSummary> spec = new OrdererIdSpec();

		Assertions.assertThat(spec).isNotNull();
	}
}
