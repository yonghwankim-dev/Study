package com.myshop.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderTest {

	private ShippingInfo shippingInfo;
	private List<OrderLine> orderLines;

	public static Stream<Arguments> shippingChangeableOrderStateSource() {
		return Stream.of(
			Arguments.of(OrderState.PAYMENT_WAITING),
			Arguments.of(OrderState.PREPARING)
		);
	}

	public static Stream<Arguments> shippingNotChangeableOrderStateSource() {
		return Stream.of(
			Arguments.of(OrderState.SHIPPED),
			Arguments.of(OrderState.DELIVERING),
			Arguments.of(OrderState.DELIVERY_COMPLETED)
		);
	}

	@BeforeEach
	void setUp() {
		shippingInfo = new ShippingInfo();
		orderLines = List.of(new OrderLine(new Product(), 1000, 2));
	}

	@ParameterizedTest
	@MethodSource(value = "shippingChangeableOrderStateSource")
	void shouldChangeShippingInfo_whenOrderStateIsPaymentWaiting(OrderState state) {
		Order order = new Order(state, shippingInfo, orderLines);
		ShippingInfo newShippingInfo = new ShippingInfo();

		order.changeShippingInfo(newShippingInfo);

		assertDoesNotThrow(() -> order.changeShippingInfo(newShippingInfo));
	}

	@ParameterizedTest
	@MethodSource(value = "shippingNotChangeableOrderStateSource")
	void shouldThrowException_whenOrderStateIsNotShippingChangeable(OrderState state) {
		List<OrderLine> orderLines = List.of(new OrderLine(new Product(), 1000, 2));
		Order order = new Order(state, shippingInfo, orderLines);
		ShippingInfo newShippingInfo = new ShippingInfo();

		Throwable throwable = Assertions.catchThrowable(() -> order.changeShippingInfo(newShippingInfo));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("can't change shipping in " + state);
	}
}
