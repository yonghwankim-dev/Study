package com.myshop.order.query.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.myshop.order.domain.OrderState;
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

	@Test
	void shouldSaveOrderSummary() {
		String number = "12345";
		OrderSummary orderSummary = createOrderSummary(number);
		orderSummaryDao.save(orderSummary);

		OrderSummary findOrderSummary = orderSummaryDao.findByNumber(number);

		Assertions.assertThat(findOrderSummary).isEqualTo(orderSummary);
	}

	private OrderSummary createOrderSummary(String number) {
		String productId = "9000000112298";
		return new OrderSummary(
			number,
			UUID.randomUUID().version(),
			"2023-01-01T10:00:00",
			"1234567890",
			10_000,
			"홍길동",
			OrderState.PAYMENT_WAITING.name(),
			LocalDateTime.now(),
			productId,
			"Java Book"
		);
	}
}
