package com.myshop.order.domain.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;

class OrdererSpecTest {

	private String ordererId;
	private OrdererSpec ordererSpec;
	private Order order;

	@BeforeEach
	void setUp() {
		ordererId = "member-1";
		ordererSpec = new OrdererSpec(ordererId);
		order = FixedDomainFactory.createOrder();
	}

	@Test
	void canCreated() {
		assertNotNull(ordererSpec);
	}

	@Test
	void shouldReturnTrue_whenOrdererIdIsMatched() {
		boolean satisfiedBy = ordererSpec.isSatisfiedBy(order);

		assertThat(satisfiedBy).isTrue();
	}

	@Test
	void shouldReturnFalse_whenOrdererIdIsNotMatched() {
		ordererSpec = new OrdererSpec("67890"); // Different ordererId

		boolean satisfiedBy = ordererSpec.isSatisfiedBy(order);

		assertThat(satisfiedBy).isFalse();
	}
}
