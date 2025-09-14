package com.myshop.order.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.FixedDomainFactory;
import com.myshop.common.model.Money;
import com.myshop.order.OrderNotFoundException;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaOrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;

	@AfterEach
	void tearDown() {
		orderRepository.deleteAll();
	}

	private void saveOrder(String orderId) {
		Order order = FixedDomainFactory.createOrder(orderId, "member-1");
		orderRepository.save(order);
	}

	@Test
	void shouldSaveOrderAndOrderLines() {
		String orderId = "1234567890";

		saveOrder(orderId);

		OrderNo orderNo = new OrderNo(orderId);
		Order findOrder = orderRepository.findById(orderNo)
			.orElseThrow(() -> new OrderNotFoundException(orderNo));
		assertNotNull(findOrder);
		Assertions.assertThat(findOrder.getTotalAmounts()).isEqualTo(new Money(2000));
		Assertions.assertThat(findOrder.getVersion()).isGreaterThan(0L);
	}

	@Test
	void shouldReturnOrderList() {
		for (int i = 1; i <= 20; i++) {
			String orderId = String.format("%05d", i);
			saveOrder(orderId);
		}

		String ordererId = "member-1";
		int startRow = 1;
		int size = 5;

		List<Order> orders = orderRepository.findByOrdererId(ordererId, startRow, size);

		Assertions.assertThat(orders).hasSize(5);
		Assertions.assertThat(orders)
			.extracting("orderNo")
			.extracting("id")
			.containsExactly("00019", "00018", "00017", "00016", "00015");
	}

	@Test
	void shouldDeleteOrder() {
		String orderId = "1234567890";
		saveOrder(orderId);
		OrderNo orderNo = new OrderNo(orderId);
		Order order = orderRepository.findById(orderNo)
			.orElseThrow(() -> new OrderNotFoundException(orderNo));

		orderRepository.delete(order);

		Assertions.assertThat(orderRepository.findById(orderNo)).isEmpty();
	}
}
