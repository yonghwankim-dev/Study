package com.myshop.board.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;
import com.myshop.board.query.dto.UpdateArticleRequest;

@Service
public class UpdateArticleService {

	private final ArticleRepository repository;

	public UpdateArticleService(ArticleRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public void update(UpdateArticleRequest request) {
		Article article = repository.findById(request.getId());
		article.changeTitle(request.getTitle());
		article.changeContent(request.getContent());
	}
}
