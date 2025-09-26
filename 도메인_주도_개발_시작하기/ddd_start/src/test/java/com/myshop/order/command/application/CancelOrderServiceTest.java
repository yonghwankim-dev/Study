package com.myshop.order.command.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.FixedDomainFactory;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.command.application.CancelOrderService;
import com.myshop.order.command.domain.model.Canceller;
import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.model.OrderState;
import com.myshop.order.command.domain.repository.OrderRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CancelOrderServiceTest {

	private OrderNo orderNo;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CancelOrderService service;

	@Autowired
	private MemberRepository memberRepository;
	private Member member;

	@BeforeEach
	void setUp() {
		member = FixedDomainFactory.createMember("member-1");
		memberRepository.save(member);

		Order order = FixedDomainFactory.createOrder();
		orderRepository.save(order);
		orderNo = order.getOrderNo();
	}

	@AfterEach
	void tearDown() {
		orderRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	void shouldDoesNotThrow_whenOrderIsNotShipped() {
		Canceller canceller = new Canceller(member.getId().getId());

		service.cancelOrder(orderNo, canceller);

		// 주문 상태가 CANCELED 인지 확인
		Order findOrder = orderRepository.findById(orderNo).orElseThrow();
		Assertions.assertThat(findOrder.getState()).isEqualTo(OrderState.CANCELED);
	}
}
