package com.myshop.catalog.application.product;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.myshop.message.domain.MessageClient;

class ViewLogServiceTest {

	private ViewLogService service;

	@BeforeEach
	void setUp() {
		MessageClient messageClient = Mockito.mock(MessageClient.class);
		service = new ViewLogService(messageClient);
	}

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void appendViewLog() {
		String memberId = "member-1";
		String productId = "product-1";
		LocalDateTime time = LocalDateTime.now();

		Assertions.assertThatCode(() -> service.appendViewLog(memberId, productId, time))
			.doesNotThrowAnyException();
	}
}
