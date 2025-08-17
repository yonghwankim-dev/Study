package com.myshop.board.infrastructure;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleContent;
import com.myshop.board.domain.ArticleRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaArticleRepositoryTest {

	@Autowired
	private ArticleRepository repository;

	@Test
	void shouldSaveArticle() {
		ArticleContent content = new ArticleContent("Test Content", "text/plain");
		Article article = new Article("Test Title", content);

		repository.save(article);

		Article findArticle = repository.findById(article.getId());
		Assertions.assertThat(findArticle).isEqualTo(article);
	}

}
