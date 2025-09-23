package com.myshop.board.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.board.application.CreateArticleService;
import com.myshop.board.query.dto.CreateArticleRequest;

@RestController
public class ArticleRestController {

	private final CreateArticleService createArticleService;

	public ArticleRestController(CreateArticleService createArticleService) {
		this.createArticleService = createArticleService;
	}

	@PostMapping("/articles")
	public ResponseEntity<Void> createArticle(CreateArticleRequest request) {
		createArticleService.create(request);
		return ResponseEntity.ok().build();
	}
}
