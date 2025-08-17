package com.myshop.board.infrastructure;

import org.springframework.stereotype.Repository;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;

@Repository
public class JpaArticleRepository implements ArticleRepository {

	private final SpringDataJpaArticleRepository repository;

	public JpaArticleRepository(SpringDataJpaArticleRepository repository) {
		this.repository = repository;
	}

	@Override
	public void save(Article article) {
		repository.save(article);
	}

	@Override
	public Article findById(Long id) {
		return repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + id));
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
