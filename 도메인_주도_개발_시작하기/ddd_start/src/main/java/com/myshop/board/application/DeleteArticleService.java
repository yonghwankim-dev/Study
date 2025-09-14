package com.myshop.board.application;

import org.springframework.stereotype.Service;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;

@Service
public class DeleteArticleService {

	private ArticleRepository articleRepository;

	public void delete(String userId, Long articleId) {
		Article article = articleRepository.findById(articleId);
		checkArticleExistence(article);
	}

	private void checkArticleExistence(Article article) {
		if (article == null) {
			throw new IllegalArgumentException("Article does not exist.");
		}
	}
}
