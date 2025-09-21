package com.myshop.order.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.common.model.Money;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberGrade;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.domain.Password;
import com.myshop.order.domain.model.Address;
import com.myshop.order.domain.model.Order;
import com.myshop.order.domain.model.OrderLine;
import com.myshop.order.domain.model.OrderNo;
import com.myshop.order.domain.model.OrderState;
import com.myshop.order.domain.model.Orderer;
import com.myshop.order.domain.model.Receiver;
import com.myshop.order.domain.model.ShippingInfo;
import com.myshop.order.domain.repository.OrderRepository;
import com.myshop.order.error.OrderNotFoundException;

class ChangeOrderServiceTest {

	private OrderNo id;
	private ChangeOrderService service;

	private ShippingInfo createNewShippingInfo() {
		Receiver newReceiver = new Receiver("Jane Doe", "0987654321");
		String message = "Please deliver to the back door.";
		Address newAddress = new Address("456 Elm St", "New City", "67890");
		return new ShippingInfo(newReceiver, message, newAddress);
	}

	@BeforeEach
	void setUp() {
		id = new OrderNo("12345");
		MemberId memberId = new MemberId("23456");
		Orderer orderer = new Orderer(memberId, "John Doe");
		ProductId productId = new ProductId("9000000112298");
		OrderLine orderLine = new OrderLine(productId, new Money(1000), 2);
		List<OrderLine> orderLines = List.of(orderLine);
		Receiver receiver = new Receiver("John Doe", "1234567890");
		String message = "Please deliver between 9 AM and 5 PM";
		Address address = new Address("123 Main St", "City", "12345");
		ShippingInfo shippingInfo = new ShippingInfo(receiver, message, address);

		Order order = new Order(id, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);

		OrderRepository orderRepository = mock(OrderRepository.class);
		given(orderRepository.findById(id))
			.willReturn(Optional.of(order));

		MemberRepository memberRepository = mock(MemberRepository.class);
		String name = "John Doe";
		Password password = new Password("password1234");
		Member member = new Member(memberId, name, address, password, MemberGrade.basic());
		given(memberRepository.findById(memberId))
			.willReturn(member);

		service = new ChangeOrderService(orderRepository, memberRepository);
	}

	@Test
	void shouldChangeShippingInfo_whenOrderExists() {
		ShippingInfo newShippingInfo = createNewShippingInfo();

		Assertions.assertDoesNotThrow(() -> service.changeShippingInfo(id, newShippingInfo, false));
	}

	@Test
	void shouldThrow_whenOrderIsNull() {
		OrderNo notExistingOrderNo = new OrderNo("99999");
		ShippingInfo newShippingInfo = createNewShippingInfo();

		Throwable throwable = catchThrowable(
			() -> service.changeShippingInfo(notExistingOrderNo, newShippingInfo, false));

		assertThat(throwable)
			.isInstanceOf(OrderNotFoundException.class)
			.hasMessage("Order not found: " + notExistingOrderNo);
	}

	@Test
	void shouldChangeAddress_whenUseNewShippingAddrAsMemberAddrIsTrue() {
		ShippingInfo newShippingInfo = createNewShippingInfo();
		boolean useNewShippingAddrAsMemberAddr = true;

		Assertions.assertDoesNotThrow(
			() -> service.changeShippingInfo(id, newShippingInfo, useNewShippingAddrAsMemberAddr));
	}
}
