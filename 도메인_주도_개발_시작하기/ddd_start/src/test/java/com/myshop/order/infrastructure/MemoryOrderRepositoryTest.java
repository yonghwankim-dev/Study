package com.myshop.order.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;

class MemoryOrderRepositoryTest {

	@Test
	void canCreated() {
		OrderRepository orderRepository = new MemoryOrderRepository();

		assertNotNull(orderRepository);
	}

	@Test
	void shouldSaveOrder() {
		OrderRepository orderRepository = new MemoryOrderRepository();
		Order order = FixedDomainFactory.createOrder();

		orderRepository.save(order);

		OrderNo orderNo = new OrderNo("1234567890");
		Optional<Order> findOrder = orderRepository.findById(orderNo);
		Assertions.assertThat(findOrder).contains(order);
	}
}
