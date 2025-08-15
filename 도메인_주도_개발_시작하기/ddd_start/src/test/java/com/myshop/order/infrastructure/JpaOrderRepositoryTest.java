package com.myshop.order.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.FixedDomainFactory;
import com.myshop.order.domain.Money;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaOrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void shouldSaveOrderAndOrderLines() {
		Order order = FixedDomainFactory.createOrder();

		orderRepository.save(order);

		OrderNo orderNo = new OrderNo("1234567890");
		Order findOrder = orderRepository.findById(orderNo);
		assertNotNull(findOrder);
		Assertions.assertThat(findOrder.getTotalAmounts()).isEqualTo(new Money(2000));
	}

}
