package com.myshop.board.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleContent;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PermissionServiceTest {

	@Autowired
	private PermissionService service;

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void checkDeletePermission() {
		String userId = "member-1";
		Article article = new Article("title", new ArticleContent("content", "text/plain"));

		Assertions.assertThatCode(() -> service.checkDeletePermission(userId, article))
			.doesNotThrowAnyException();
	}
}
