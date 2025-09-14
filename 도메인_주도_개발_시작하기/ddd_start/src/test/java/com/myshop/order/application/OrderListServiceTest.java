package com.myshop.order.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.order.domain.OrderRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderListServiceTest {

	@Autowired
	private OrderListService service;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void getOrderList() {
		String memberId = "member-1";
		Assertions.assertThat(service.getOrderList(memberId)).isEmpty();
	}

}
