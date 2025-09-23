package com.myshop.board.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;

@Service
public class DetailArticleService {

	private final ArticleRepository repository;

	public DetailArticleService(ArticleRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Article getArticle(Long id) {
		return repository.findById(id);
	}
}
