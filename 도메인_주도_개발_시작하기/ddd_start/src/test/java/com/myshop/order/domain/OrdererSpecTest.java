package com.myshop.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrdererSpecTest {

	@Test
	void canCreated() {
		String ordererId = "12345";
		OrdererSpec ordererSpec = new OrdererSpec(ordererId);

		assertNotNull(ordererSpec);
	}

}
