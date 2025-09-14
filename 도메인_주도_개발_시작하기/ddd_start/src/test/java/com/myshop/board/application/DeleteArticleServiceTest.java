package com.myshop.board.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DeleteArticleServiceTest {

	@Test
	void canCreated() {
		DeleteArticleService service = new DeleteArticleService();

		Assertions.assertThat(service).isNotNull();
	}

}
