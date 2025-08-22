package com.myshop.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;

class OrdererSpecTest {

	@Test
	void canCreated() {
		String ordererId = "12345";
		OrdererSpec ordererSpec = new OrdererSpec(ordererId);

		assertNotNull(ordererSpec);
	}

	@Test
	void shouldReturnTrue_whenOrdererIdIsMatched() {
		String ordererId = "12345";
		OrdererSpec ordererSpec = new OrdererSpec(ordererId);
		Order order = FixedDomainFactory.createOrder();

		boolean satisfiedBy = ordererSpec.isSatisfiedBy(order);

		Assertions.assertThat(satisfiedBy).isTrue();
	}
}
