package com.myshop.board.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.myshop.board.application.UpdateArticleService;
import com.myshop.board.query.dto.UpdateArticleRequest;

@RestController
public class UpdateArticleRestController {

	private final UpdateArticleService service;

	public UpdateArticleRestController(UpdateArticleService service) {
		this.service = service;
	}

	@PostMapping("/articles/edit")
	public ModelAndView updateArticle(UpdateArticleRequest request) {
		service.update(request);
		return new ModelAndView("articles/detail")
			.addObject("id", request.getId());
	}
}
