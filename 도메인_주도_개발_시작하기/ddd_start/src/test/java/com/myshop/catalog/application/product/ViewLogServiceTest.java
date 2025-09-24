package com.myshop.catalog.application.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ViewLogServiceTest {

	@Test
	void canCreated() {
		ViewLogService service = new ViewLogService();

		Assertions.assertThat(service).isNotNull();
	}
}
