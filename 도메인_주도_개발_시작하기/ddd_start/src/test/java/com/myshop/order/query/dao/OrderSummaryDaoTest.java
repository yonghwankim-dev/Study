package com.myshop.order.query.dao;

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
}
