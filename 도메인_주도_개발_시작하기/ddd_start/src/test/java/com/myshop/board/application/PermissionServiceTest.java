package com.myshop.board.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleContent;
import com.myshop.board.domain.ArticleRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PermissionServiceTest {

	@Autowired
	private PermissionService service;

	@Autowired
	private ArticleRepository articleRepository;
	private Article article;

	@BeforeEach
	void setUp() {
		ArticleContent articleContent = new ArticleContent("content", "text/plain");
		String memberId = "member-1";
		article = new Article("Title 1", articleContent, memberId);
		articleRepository.save(article);
	}

	@AfterEach
	void tearDown() {
		articleRepository.deleteById(article.getId());
	}

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void checkDeletePermission() {
		String userId = "member-1";

		Assertions.assertThatCode(() -> service.checkDeletePermission(userId, article))
			.doesNotThrowAnyException();
	}

	@Test
	void shouldThrowException_whenUserHasNoPermission() {
		String userId = "member-2";

		Throwable throwable = Assertions.catchThrowable(() -> service.checkDeletePermission(userId, article));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("User does not have permission to delete this article.");
	}
}
