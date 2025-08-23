package com.myshop.order.query.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.myshop.order.query.dto.OrderSummary;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderSummaryDaoTest {

	@Autowired
	private OrderSummaryDao orderSummaryDao;

	@Test
	void shouldReturnOrderSummary() {
		Specification<OrderSummary> spec = OrderSummarySpecs.ordererId("12345");

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).isNotNull();
	}

	@Test
	void shouldReturnOrderSummary_whenTwoSpecifications() {
		Specification<OrderSummary> spec1 = OrderSummarySpecs.ordererId("12345");
		Specification<OrderSummary> spec2 = OrderSummarySpecs.orderDateBetween(
			LocalDateTime.of(2023, 1, 1, 0, 0),
			LocalDateTime.of(2023, 12, 31, 23, 59)
		);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec1.and(spec2));

		Assertions.assertThat(orderSummaries).isNotNull();
	}

	@Test
	void shouldReturnOrderSummaryByOrdererId() {
		List<OrderSummary> orderSummaries = orderSummaryDao.findByOrdererIdOrderByNumberDesc("12345");

		Assertions.assertThat(orderSummaries).isNotNull();
	}

	@Test
	void shouldReturnOrderList_whenPassSpecification() {
		Specification<OrderSummary> spec = new OrdererIdSpec("12345");

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).isNotNull();
	}
}
