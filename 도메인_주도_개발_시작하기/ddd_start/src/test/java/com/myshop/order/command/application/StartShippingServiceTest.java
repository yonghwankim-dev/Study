package com.myshop.order.command.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionTemplate;

import com.myshop.FixedDomainFactory;
import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderState;
import com.myshop.order.command.domain.repository.OrderRepository;
import com.myshop.order.error.VersionConflictException;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StartShippingServiceTest {

	@Autowired
	private StartShippingService service;

	@Autowired
	private SearchOrderService searchOrderService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;
	private Order order;

	@BeforeEach
	void setUp() {
		order = FixedDomainFactory.createOrder();
		order.changeState(OrderState.PREPARING);
		orderRepository.save(order);

		setAdminAuthentication();
	}

	@AfterEach
	void tearDown() {
		SecurityContextHolder.clearContext();
		transactionTemplate.executeWithoutResult(status -> orderRepository.deleteAll());
	}

	private void setAdminAuthentication() {
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

	@Test
	void shouldThrowException_whenVersionConflict() {
		Order findOrder = orderRepository.findById(this.order.getOrderNo()).orElseThrow();
		long version = findOrder.getVersion() + 1;
		StartShippingRequest request = new StartShippingRequest(findOrder.getOrderNo().getId(), version);

		Throwable throwable = Assertions.catchThrowable(() -> service.startShipping(request));

		Assertions.assertThat(throwable)
			.isInstanceOf(VersionConflictException.class);
	}
}
