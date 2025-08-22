package com.myshop.order.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.myshop.order.domain.OrderRepository;

class MemoryOrderRepositoryTest {

	@Test
	void canCreated() {
		OrderRepository orderRepository = new MemoryOrderRepository();

		assertNotNull(orderRepository);
	}
}
