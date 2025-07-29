package com.nemo.effective_java.item63.step01;

import org.junit.jupiter.api.Test;

class StringClientTest {
	@Test
	void testStatement() {
		// given
		StringClient stringClient = new StringClient();
		// when
		String result = stringClient.statement(100_000);
		// then
		System.out.println(result);
	}

}
