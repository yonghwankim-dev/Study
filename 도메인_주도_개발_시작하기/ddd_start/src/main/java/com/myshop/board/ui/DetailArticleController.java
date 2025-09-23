package com.myshop.board.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myshop.board.application.DetailArticleService;
import com.myshop.board.domain.Article;
import com.myshop.board.query.dto.ArticleItem;

@Controller
public class DetailArticleController {

	private final DetailArticleService service;

	public DetailArticleController(DetailArticleService service) {
		this.service = service;
	}

	@GetMapping("/articles/detail")
	public String detail(@RequestParam("id") Long id, ModelMap model) {
		Article article = service.getArticle(id);
		ArticleItem item = new ArticleItem(article.getId(), article.getTitle(), article.getContent().getContent());
		model.addAttribute("article", item);
		return "articles/detail";
	}
}
