package com.myshop.board.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;

@Service
public class DeleteArticleService {

	private final ArticleRepository articleRepository;
	private final PermissionService permissionService;

	public DeleteArticleService(ArticleRepository articleRepository, PermissionService permissionService) {
		this.articleRepository = articleRepository;
		this.permissionService = permissionService;
	}

	@Transactional
	public void delete(String userId, Long articleId) {
		Article article = articleRepository.findById(articleId);
		checkArticleExistence(article);
		article.markDeleted();
	}

	private void checkArticleExistence(Article article) {
		if (article == null) {
			throw new IllegalArgumentException("Article does not exist.");
		}
	}
}
