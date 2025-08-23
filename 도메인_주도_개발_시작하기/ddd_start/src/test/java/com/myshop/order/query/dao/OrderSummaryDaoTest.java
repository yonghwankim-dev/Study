package com.myshop.order.query.dao;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
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
		saveOrderSummaries();
	}

	@Test
	void shouldReturnOrderSummary() {
		Specification<OrderSummary> spec = OrderSummarySpecs.ordererId(ordererId);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).isNotNull();
	}

	@Test
	void shouldReturnOrderSummaryByOrdererIdAndDateRange() {
		LocalDateTime now = LocalDateTime.now();
		Specification<OrderSummary> spec1 = OrderSummarySpecs.ordererId(ordererId);
		Specification<OrderSummary> spec2 = OrderSummarySpecs.orderDateBetween(
			now.minusMonths(1),
			now.plusMonths(1)
		);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec1.and(spec2));

		Assertions.assertThat(orderSummaries).hasSize(20);
	}

	@Test
	void shouldReturnEmptyOrderSummary_whenNoMatchingOrdererId() {
		Specification<OrderSummary> spec = Specification.not(OrderSummarySpecs.ordererId(ordererId));

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).isEmpty();
	}

	@Test
	void shouldReturnOrderSummary_whenSpecIsNullable() {
		Specification<OrderSummary> nullableSpec = createNullableSpec();
		Specification<OrderSummary> otherSpec = OrderSummarySpecs.ordererId(ordererId);
		Specification<OrderSummary> spec = Specification.where(nullableSpec).and(otherSpec);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).hasSize(20);
	}

	private Specification<OrderSummary> createNullableSpec() {
		return null;
	}

	@Test
	void shouldReturnOrderSummaryByOrdererId() {
		List<OrderSummary> orderSummaries = orderSummaryDao.findByOrdererIdOrderByNumberDesc("12345");

		Assertions.assertThat(orderSummaries).hasSize(20);
		Comparator<OrderSummary> comparator = Comparator.comparing(OrderSummary::getNumber).reversed();
		Assertions.assertThat(orderSummaries)
			.isSortedAccordingTo(comparator);
	}

	@Test
	void shouldReturnOrderSummaryByOrdererIdAndOrderDate() {
		List<OrderSummary> orderSummaries = orderSummaryDao.findByOrdererIdOrderByOrderDateDescNumberAsc(ordererId);

		Assertions.assertThat(orderSummaries).hasSize(20);
		Comparator<OrderSummary> comparator = Comparator.comparing(OrderSummary::getOrderDate,
				Comparator.reverseOrder())
			.thenComparing(OrderSummary::getNumber);
		Assertions.assertThat(orderSummaries)
			.isSortedAccordingTo(comparator);
	}

	@Test
	void shouldReturnOrderList_whenPassSpecification() {
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

	@Test
	void shouldReturnOrderSummaryListByOrdererIdAndSort() {
		Sort sort = Sort.by("number").ascending();

		List<OrderSummary> orderSummaries = orderSummaryDao.findByOrdererId(ordererId, sort);

		Assertions.assertThat(orderSummaries)
			.hasSize(20)
			.isSortedAccordingTo(Comparator.comparing(OrderSummary::getNumber));
	}
}
