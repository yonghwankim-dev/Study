package com.myshop.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;
import com.myshop.order.infrastructure.domain.SecurityCancelPolicy;

class SecurityCancelPolicyTest {

	private CancelPolicy cancelPolicy;

	@BeforeEach
	void setUp() {
		cancelPolicy = new SecurityCancelPolicy();
	}

	@Test
	void canCreated() {
		assertNotNull(cancelPolicy);
	}

	@Test
	void shouldReturnTrue_whenCancellerOrderer() {
		Order order = FixedDomainFactory.createOrder();
		Canceller canceller = new Canceller("member-1");

		boolean result = cancelPolicy.hasCancellationPermission(order, canceller);

		Assertions.assertThat(result).isTrue();
	}

	@Test
	void shouldReturnFalse_whenCancellerNotOrderer() {
		Order order = FixedDomainFactory.createOrder();
		Canceller canceller = new Canceller("54321");

		boolean result = cancelPolicy.hasCancellationPermission(order, canceller);

		Assertions.assertThat(result).isFalse();
	}
}
