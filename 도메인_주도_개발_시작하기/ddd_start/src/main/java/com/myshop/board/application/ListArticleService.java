package com.myshop.board.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;

@Service
public class ListArticleService {

	private final ArticleRepository repository;

	public ListArticleService(ArticleRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public List<Article> getAllArticles() {
		return repository.findAll();
	}
}
