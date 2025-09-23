package com.myshop.board.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleContent;
import com.myshop.board.domain.ArticleRepository;
import com.myshop.board.query.dto.CreateArticleRequest;

@Service
public class CreateArticleService {

	private final ArticleRepository repository;

	public CreateArticleService(ArticleRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public void create(CreateArticleRequest request) {
		String title = request.getTitle();
		ArticleContent content = new ArticleContent(request.getContent(), "text/plain");
		String authorId = request.getAuthorId();
		Article article = new Article(title, content, authorId);
		repository.save(article);
	}
}
