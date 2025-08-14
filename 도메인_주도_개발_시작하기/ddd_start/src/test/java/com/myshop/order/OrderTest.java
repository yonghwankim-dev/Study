package com.myshop.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.myshop.member.domain.MemberId;
import com.myshop.order.domain.Address;
import com.myshop.order.domain.Money;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderLine;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderState;
import com.myshop.order.domain.Orderer;
import com.myshop.order.domain.Product;
import com.myshop.order.domain.Receiver;
import com.myshop.order.domain.ShippingInfo;

class OrderTest {

	private ShippingInfo shippingInfo;
	private List<OrderLine> orderLines;
	private ShippingInfo newShippingInfo;
	private Orderer orderer;
	private OrderNo orderNo;

	public static Stream<Arguments> notShippedOrderStateSource() {
		return Stream.of(
			Arguments.of(OrderState.PAYMENT_WAITING),
			Arguments.of(OrderState.PREPARING)
		);
	}

	public static Stream<Arguments> alreadyShippedOrderStateSource() {
		return Stream.of(
			Arguments.of(OrderState.SHIPPED),
			Arguments.of(OrderState.DELIVERING),
			Arguments.of(OrderState.DELIVERY_COMPLETED),
			Arguments.of(OrderState.CANCELED)
		);
	}

	public static Stream<Arguments> invalidOrderLinesSource() {
		return Stream.of(
			Arguments.of(List.of()),
			Arguments.of((Object)null)
		);
	}

	@BeforeEach
	void setUp() {
		orderNo = new OrderNo("12345");
		MemberId memberId = new MemberId("12345");
		orderer = new Orderer(memberId, "John Doe", "johnDoe@gmail.com");
		Receiver receiver = new Receiver("John Doe", "1234567890");
		Address address = new Address("123 Main St", "City", "12345");
		shippingInfo = new ShippingInfo(receiver, address);
		orderLines = List.of(new OrderLine(new Product(), new Money(1000), 2));
		Receiver newReceiver = new Receiver("Jane Doe", "0987654321");
		Address newAddress = new Address("456 Elm St", "New City", "67890");
		newShippingInfo = new ShippingInfo(newReceiver, newAddress);
	}

	@ParameterizedTest
	@MethodSource(value = "notShippedOrderStateSource")
	void shouldChangeShippingInfo_whenOrderStateIsPaymentWaiting(OrderState state) {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, state);

		order.changeShippingInfo(newShippingInfo);

		assertDoesNotThrow(() -> order.changeShippingInfo(newShippingInfo));
	}

	@ParameterizedTest
	@MethodSource(value = "alreadyShippedOrderStateSource")
	void shouldThrowException_whenOrderStateIsNotShippingChangeable(OrderState state) {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, state);

		Throwable throwable = Assertions.catchThrowable(() -> order.changeShippingInfo(newShippingInfo));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("already shipped");
	}

	@ParameterizedTest
	@MethodSource(value = "invalidOrderLinesSource")
	void shouldThrowException_whenOrderLinesIsInvalid(List<OrderLine> orderLines) {
		Throwable throwable = Assertions.catchThrowable(() -> new Order(orderNo, orderer, orderLines, shippingInfo,
			OrderState.PAYMENT_WAITING));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no OrderLine");
	}

	@Test
	void shouldNotThrowException_whenShippingInfoIsValid() {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);

		assertDoesNotThrow(() -> order.changeShippingInfo(newShippingInfo));
	}

	@Test
	void shouldThrowException_whenShippingInfoIsNull(){
		Throwable throwable = Assertions.catchThrowable(() -> new Order(orderNo, orderer, orderLines, null, OrderState.PAYMENT_WAITING));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no ShippingInfo");
	}

	@ParameterizedTest
	@MethodSource(value = "notShippedOrderStateSource")
	void shouldDoesNotThrow_whenOrderIsCanceled() {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);

		assertDoesNotThrow(order::cancel);
	}

	@ParameterizedTest
	@MethodSource(value = "alreadyShippedOrderStateSource")
	void shouldThrow_whenOrderIsAlreadyShipped(OrderState state) {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, state);

		Throwable throwable = Assertions.catchThrowable(order::cancel);

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("already shipped");
	}

	@Test
	void shouldDoesNotThrow_whenOrdererIsNull() {
		Throwable throwable = Assertions.catchThrowable(
			() -> new Order(orderNo, null, orderLines, shippingInfo, OrderState.PAYMENT_WAITING));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no Orderer");
	}

	@Test
	void shouldThrow_whenOrderNoIsNull() {
		Throwable throwable = Assertions.catchThrowable(
			() -> new Order(null, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no OrderNo");
	}

	@Test
	void shouldReturnTrue_whenCompareOrder(){
		Order order1 = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);
		Order order2 = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.SHIPPED);

		boolean actual = order1.equals(order2);

		assertTrue(actual, "Orders should be equal based on OrderNo");
	}
}
