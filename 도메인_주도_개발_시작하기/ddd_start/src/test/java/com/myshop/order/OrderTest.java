package com.myshop.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTest {

	public static Stream<Arguments> shippingChangeableOrderStateSource() {
		return Stream.of(
			Arguments.of(OrderState.PAYMENT_WAITING),
			Arguments.of(OrderState.PREPARING)
		);
	}

	@ParameterizedTest
	@MethodSource(value = "shippingChangeableOrderStateSource")
	void changeShippingInfo_shouldChangeShippingInfo_whenOrderStateIsPaymentWaiting(OrderState state) {
		ShippingInfo oldShippingInfo = new ShippingInfo();
		Order order = new Order(state, oldShippingInfo);
		ShippingInfo newShippingInfo = new ShippingInfo();

		order.changeShippingInfo(newShippingInfo);

		assertDoesNotThrow(() -> order.changeShippingInfo(newShippingInfo));
	}
}
