package com.myshop.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;

class OrdererSpecTest {

	private String ordererId;
	private OrdererSpec ordererSpec;

	@BeforeEach
	void setUp() {
		ordererId = "12345";
		ordererSpec = new OrdererSpec(ordererId);
	}

	@Test
	void canCreated() {
		assertNotNull(ordererSpec);
	}

	@Test
	void shouldReturnTrue_whenOrdererIdIsMatched() {
		Order order = FixedDomainFactory.createOrder();

		boolean satisfiedBy = ordererSpec.isSatisfiedBy(order);

		Assertions.assertThat(satisfiedBy).isTrue();
	}
}
