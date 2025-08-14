package com.myshop.order.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.domain.Address;
import com.myshop.order.domain.Money;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderLine;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;
import com.myshop.order.domain.OrderState;
import com.myshop.order.domain.Orderer;
import com.myshop.order.domain.Product;
import com.myshop.order.domain.Receiver;
import com.myshop.order.domain.ShippingInfo;

class ChangeOrderServiceTest {

	private Order order;
	private OrderNo id;
	private OrderRepository orderRepository;
	private MemberRepository memberRepository;
	private ChangeOrderService service;
	private MemberId memberId;

	private ShippingInfo createNewShippingInfo() {
		Receiver newReceiver = new Receiver("Jane Doe", "0987654321");
		Address newAddress = new Address("456 Elm St", "New City", "67890");
		return new ShippingInfo(newReceiver, newAddress);
	}

	@BeforeEach
	void setUp() {
		id = new OrderNo("12345");
		memberId = new MemberId("23456");
		Orderer orderer = new Orderer(memberId, "John Doe", "johnDoe@gmail.com");
		OrderLine orderLine = new OrderLine(new Product(), new Money(1000), 2);
		List<OrderLine> orderLines = List.of(orderLine);
		Receiver receiver = new Receiver("John Doe", "1234567890");
		Address address = new Address("123 Main St", "City", "12345");
		ShippingInfo shippingInfo = new ShippingInfo(receiver,address);

		order = new Order(id, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);

		orderRepository = mock(OrderRepository.class);
		given(orderRepository.findById(id))
			.willReturn(order);

		memberRepository = mock(MemberRepository.class);
		Member member = new Member(memberId, address);
		given(memberRepository.findById(memberId))
			.willReturn(member);

		service = new ChangeOrderService(orderRepository, memberRepository);
	}

	@Test
	void shouldChangeShippingInfo_whenOrderExists() {
		ShippingInfo newShippingInfo = createNewShippingInfo();

		Assertions.assertDoesNotThrow(()-> service.changeShippingInfo(id, newShippingInfo, false));
	}

	@Test
	void shouldChangeAddress_whenUseNewShippingAddrAsMemberAddrIsTrue() {
		ShippingInfo newShippingInfo = createNewShippingInfo();
		boolean useNewShippingAddrAsMemberAddr = true;

		Assertions.assertDoesNotThrow(() -> service.changeShippingInfo(id, newShippingInfo, useNewShippingAddrAsMemberAddr));
	}
}
