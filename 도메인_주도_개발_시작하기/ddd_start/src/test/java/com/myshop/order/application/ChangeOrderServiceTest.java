package com.myshop.order.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

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

	private ShippingInfo createNewShippingInfo() {
		Receiver newReceiver = new Receiver("Jane Doe", "0987654321");
		Address newAddress = new Address("456 Elm St", "New City", "67890");
		return new ShippingInfo(newReceiver, newAddress);
	}

	@BeforeEach
	void setUp() {
		id = new OrderNo("12345");
		MemberId memberId = new MemberId("23456");
		Orderer orderer = new Orderer(memberId, "John Doe", "johnDoe@gmail.com");
		OrderLine orderLine = new OrderLine(new Product(), new Money(1000), 2);
		List<OrderLine> orderLines = List.of(orderLine);
		ShippingInfo shippingInfo = new ShippingInfo(
			new Receiver("John Doe", "1234567890"),
			new Address("123 Main St", "City", "12345")
		);

		order = new Order(id, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);

		orderRepository = mock(OrderRepository.class);
		given(orderRepository.findById(id))
			.willReturn(order);
		memberRepository = mock(MemberRepository.class);
	}

	@Test
	void canCreated(){
		ChangeOrderService service = new ChangeOrderService(orderRepository, memberRepository);

		assertNotNull(service);
	}

	@Test
	void shouldChangeShippingInfo_whenOrderExists() {
		ChangeOrderService service = new ChangeOrderService(orderRepository, memberRepository);
		ShippingInfo newShippingInfo = createNewShippingInfo();

		Assertions.assertDoesNotThrow(()->service.changeShippingInfo(id, newShippingInfo, false));
	}
}
