package com.myshop.board.domain;

import java.util.List;

public interface ArticleRepository {
	void save(Article article);

	Article findById(Long id);

	List<Article> findAll();

	void deleteById(Long id);
}
