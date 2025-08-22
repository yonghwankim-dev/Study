package com.myshop.order.query.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import com.myshop.order.query.dto.OrderSummary;

class OrderSummarySpecsTest {

	@Test
	void shouldReturnOrderDateBetweenSpecification() {
		LocalDateTime from = LocalDateTime.of(2023, 1, 1, 0, 0);
		LocalDateTime to = LocalDateTime.of(2023, 12, 31, 23, 59);
		Specification<OrderSummary> specification = OrderSummarySpecs.orderDateBetween(from, to);
		assertNotNull(specification);
	}
}
