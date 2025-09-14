package com.myshop.board.application;

import org.assertj.core.api.Assertions;
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
class DeleteArticleServiceTest {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private DeleteArticleService service;
	private Long articleId;

	@BeforeEach
	void setUp() {
		ArticleContent articleContent = new ArticleContent("content", "text/plain");
		String memberId = "member-1";
		Article article = new Article("Title 1", articleContent, memberId);
		articleRepository.save(article);
		articleId = article.getId();
	}

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void delete() {
		String userId = "member-1";

		service.delete(userId, articleId);

		Article findArticle = articleRepository.findById(articleId);
		Assertions.assertThat(findArticle.isDeleted()).isTrue();
	}

	@Test
	void shouldNotDeleteArticle_whenNoPermission() {
		String userId = "member-2";

		Throwable throwable = Assertions.catchThrowable(() -> service.delete(userId, articleId));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("User does not have permission to delete this article.");
	}

}
