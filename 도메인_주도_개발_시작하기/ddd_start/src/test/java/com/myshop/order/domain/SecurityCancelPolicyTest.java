package com.myshop.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;
import com.myshop.order.infrastructure.domain.SecurityCancelPolicy;

class SecurityCancelPolicyTest {

	@Test
	void canCreated() {
		CancelPolicy cancelPolicy = new SecurityCancelPolicy();
		assertNotNull(cancelPolicy);
	}

	@Test
	void shouldReturnTrue_whenCancellerHasNotPermission() {
		CancelPolicy cancelPolicy = new SecurityCancelPolicy();
		Order order = FixedDomainFactory.createOrder();
		Canceller canceller = new Canceller("12345");

		boolean result = cancelPolicy.hasCancellationPermission(order, canceller);

		Assertions.assertThat(result).isTrue();
	}
}
