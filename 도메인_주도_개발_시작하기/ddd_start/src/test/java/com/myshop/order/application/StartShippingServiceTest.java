package com.myshop.order.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.myshop.FixedDomainFactory;
import com.myshop.order.domain.model.Order;
import com.myshop.order.domain.model.OrderState;
import com.myshop.order.domain.repository.OrderRepository;
import com.myshop.order.query.dto.StartShippingRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StartShippingServiceTest {

	@Autowired
	private StartShippingService service;

	@Autowired
	private SearchOrderService searchOrderService;

	@Autowired
	private OrderRepository orderRepository;
	private Order order;

	@BeforeEach
	void setUp() {
		order = FixedDomainFactory.createOrder();
		order.changeState(OrderState.PREPARING);
		orderRepository.save(order);

		Authentication authentication = FixedDomainFactory.createAdminAuthentication();
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Test
	void startShipping() {
		Order findOrder = orderRepository.findById(this.order.getOrderNo()).orElseThrow();
		StartShippingRequest request = new StartShippingRequest(findOrder.getOrderNo().getId(), findOrder.getVersion());

		service.startShipping(request);

		findOrder = searchOrderService.search(this.order.getOrderNo());
		Assertions.assertThat(findOrder.getState()).isEqualTo(OrderState.SHIPPED);
	}
}
