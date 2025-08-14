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

class OrderTest {

	private ShippingInfo shippingInfo;
	private List<OrderLine> orderLines;
	private ShippingInfo newShippingInfo;

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

	public static Stream<Arguments> invalidOrderLinesSource() {
		return Stream.of(
			Arguments.of(List.of()),
			Arguments.of((Object)null)
		);
	}

	@BeforeEach
	void setUp() {
		shippingInfo = new ShippingInfo("John Doe", "123 Main St", "12345", "City", "Country");
		orderLines = List.of(new OrderLine(new Product(), 1000, 2));
		newShippingInfo = new ShippingInfo("Jane Doe", "456 Elm St", "67890", "New City", "New Country");
	}

	@ParameterizedTest
	@MethodSource(value = "shippingChangeableOrderStateSource")
	void shouldChangeShippingInfo_whenOrderStateIsPaymentWaiting(OrderState state) {
		Order order = new Order(state, shippingInfo, orderLines);

		order.changeShippingInfo(newShippingInfo);

		assertDoesNotThrow(() -> order.changeShippingInfo(newShippingInfo));
	}

	@ParameterizedTest
	@MethodSource(value = "shippingNotChangeableOrderStateSource")
	void shouldThrowException_whenOrderStateIsNotShippingChangeable(OrderState state) {
		Order order = new Order(state, shippingInfo, orderLines);

		Throwable throwable = Assertions.catchThrowable(() -> order.changeShippingInfo(newShippingInfo));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("can't change shipping in " + state);
	}

	@ParameterizedTest
	@MethodSource(value = "invalidOrderLinesSource")
	void shouldThrowException_whenOrderLinesIsInvalid(List<OrderLine> orderLines) {
		Throwable throwable = Assertions.catchThrowable(() -> new Order(OrderState.PAYMENT_WAITING, shippingInfo, orderLines));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no OrderLine");
	}

	@Test
	void shouldNotThrowException_whenShippingInfoIsValid() {
		Order order = new Order(OrderState.PAYMENT_WAITING, shippingInfo, orderLines);

		assertDoesNotThrow(() -> order.changeShippingInfo(newShippingInfo));
	}

	@Test
	void shouldThrowException_whenShippingInfoIsNull(){
		Throwable throwable = Assertions.catchThrowable(() -> new Order(OrderState.PAYMENT_WAITING, null, orderLines));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no ShippingInfo");
	}
}
