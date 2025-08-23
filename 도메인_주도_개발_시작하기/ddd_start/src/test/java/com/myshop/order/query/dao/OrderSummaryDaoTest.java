package com.myshop.order.query.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.myshop.FixedDomainFactory;
import com.myshop.order.query.dto.OrderSummary;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderSummaryDaoTest {

	@Autowired
	private OrderSummaryDao orderSummaryDao;
	private String ordererId;

	private void saveOrderSummaries() {
		for (int i = 1; i <= 20; i++) {
			String number = String.format("%05d", i);
			OrderSummary orderSummary = FixedDomainFactory.createOrderSummary(number);
			orderSummaryDao.save(orderSummary);
		}
	}

	@AfterEach
	void tearDown() {
		orderSummaryDao.deleteAll();
	}

	@BeforeEach
	void setUp() {
		ordererId = "12345";
	}

	@Test
	void shouldReturnOrderSummary() {
		Specification<OrderSummary> spec = OrderSummarySpecs.ordererId(ordererId);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).isNotNull();
	}

	@Test
	void shouldReturnOrderSummary_whenTwoSpecifications() {
		Specification<OrderSummary> spec1 = OrderSummarySpecs.ordererId(ordererId);
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
		saveOrderSummaries();
		Specification<OrderSummary> spec = new OrdererIdSpec(ordererId);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).hasSize(20);
	}

	@Test
	void shouldSaveOrderSummary() {
		OrderSummary orderSummary = FixedDomainFactory.createOrderSummary(ordererId);
		orderSummaryDao.save(orderSummary);

		OrderSummary findOrderSummary = orderSummaryDao.findByNumber(ordererId);

		Assertions.assertThat(findOrderSummary).isEqualTo(orderSummary);
	}
}
