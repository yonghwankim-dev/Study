package com.myshop.catalog.application.product;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ViewLogServiceTest {

	@Test
	void canCreated() {
		ViewLogService service = new ViewLogService();

		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void appendViewLog() {
		ViewLogService service = new ViewLogService();
		String memberId = "member-1";
		String productId = "product-1";
		LocalDateTime time = LocalDateTime.now();

		Assertions.assertThatCode(() -> service.appendViewLog(memberId, productId, time))
			.doesNotThrowAnyException();
	}
}
