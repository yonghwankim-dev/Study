package com.myshop.order.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;
import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.model.OrderNoGenerator;
import com.myshop.order.command.domain.model.OrdererSpec;
import com.myshop.order.command.domain.model.Specification;
import com.myshop.order.command.domain.repository.OrderRepository;
import com.myshop.order.command.infrastructure.MemoryOrderRepository;

class MemoryOrderRepositoryTest {

	private OrderRepository orderRepository;

	private List<Order> saveOrders(OrderRepository orderRepository) {
		List<Order> result = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			String orderId = String.format("%05d", i);
			Order order = FixedDomainFactory.createOrder(orderId, "member-1");
			orderRepository.save(order);
			result.add(order);
		}
		return result;
	}

	@BeforeEach
	void setUp() {
		OrderNoGenerator orderNoGenerator = new NanoTimeOrderNoGenerator();
		orderRepository = new MemoryOrderRepository(orderNoGenerator);
	}

	@Test
	void canCreated() {
		assertNotNull(orderRepository);
	}

	@Test
	void shouldSaveOrder() {
		Order order = FixedDomainFactory.createOrder();

		orderRepository.save(order);

		OrderNo orderNo = new OrderNo("1234567890");
		Optional<Order> findOrder = orderRepository.findById(orderNo);
		Assertions.assertThat(findOrder).contains(order);
	}

	@Test
	void shouldSubOrders() {
		List<Order> saveOrders = saveOrders(orderRepository);
		String ordererId = "member-1";
		int startRow = 1;
		int size = 10;

		List<Order> orders = orderRepository.findByOrdererId(ordererId, startRow, size);

		assertNotNull(orders);

		List<Order> expectedOrders = saveOrders.stream()
			.sorted((order1, order2) -> order2.getOrderNo().compareTo(order1.getOrderNo()))
			.skip(startRow)
			.limit(size)
			.toList();
		Assertions.assertThat(orders)
			.containsExactlyElementsOf(expectedOrders)
			.hasSize(10);
	}

	@Test
	void shouldDeleteOrder() {
		Order order = FixedDomainFactory.createOrder();
		orderRepository.save(order);

		orderRepository.delete(order);

		orderRepository.findById(new OrderNo("1234567890"))
			.ifPresentOrElse(
				ord -> fail("Order should be deleted"),
				() -> assertTrue(true)
			);
	}

	@Test
	void shouldDeleteAllOrders() {
		saveOrders(orderRepository);

		orderRepository.deleteAll();

		List<Order> orders = orderRepository.findByOrdererId("12345", 0, 10);
		Assertions.assertThat(orders).isEmpty();
	}

	@Test
	void shouldReturnOrders_whenOrdererIdIsMatched() {
		List<Order> saveOrders = saveOrders(orderRepository);
		Specification<Order> spec = new OrdererSpec("member-1");

		List<Order> orders = orderRepository.findAll(spec);
		Assertions.assertThat(orders)
			.containsExactlyElementsOf(saveOrders)
			.hasSize(20);
	}
}
