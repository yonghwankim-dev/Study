package com.myshop.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;

class CancelPolicyTest {

	@Test
	void canCreated() {
		CancelPolicy cancelPolicy = new CancelPolicy();
		assertNotNull(cancelPolicy);
	}

	@Test
	void shouldReturnTrue_whenCancellerHasNotPermission() {
		CancelPolicy cancelPolicy = new CancelPolicy();
		Order order = FixedDomainFactory.createOrder();
		Canceller canceller = new Canceller("12345");

		boolean result = cancelPolicy.hasCancellationPermission(order, canceller);

		Assertions.assertThat(result).isTrue();
	}
}
