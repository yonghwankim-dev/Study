package com.myshop.board.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.board.application.UpdateArticleService;
import com.myshop.board.query.dto.UpdateArticleRequest;

@RestController
public class UpdateArticleRestController {

	private final UpdateArticleService service;

	public UpdateArticleRestController(UpdateArticleService service) {
		this.service = service;
	}

	@PostMapping("/articles/edit")
	public ResponseEntity<Void> updateArticle(UpdateArticleRequest request) {
		service.update(request);
		return ResponseEntity.status(HttpStatus.FOUND)
			.location(java.net.URI.create("/articles/detail?id=" + request.getId()))
			.build();
	}
}
