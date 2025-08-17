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

	@Test
	void shouldSaveOrderAndOrderLines() {
		Order order = FixedDomainFactory.createOrder();

		orderRepository.save(order);

		OrderNo orderNo = new OrderNo("1234567890");
		Order findOrder = orderRepository.findById(orderNo)
			.orElseThrow(() -> new OrderNotFoundException(orderNo));
		assertNotNull(findOrder);
		Assertions.assertThat(findOrder.getTotalAmounts()).isEqualTo(new Money(2000));
	}

	@Test
	void shouldReturnOrderList() {
		String ordererId = "1234567890";
		int startRow = 0;
		int size = 10;

		List<Order> orders = orderRepository.findByOrdererId(ordererId, startRow, size);

		Assertions.assertThat(orders).isEmpty();
	}
}
