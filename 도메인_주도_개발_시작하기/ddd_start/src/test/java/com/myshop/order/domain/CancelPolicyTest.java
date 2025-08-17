package com.myshop.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CancelPolicyTest {

	@Test
	void canCreated() {
		CancelPolicy cancelPolicy = new CancelPolicy();
		assertNotNull(cancelPolicy);
	}
}
