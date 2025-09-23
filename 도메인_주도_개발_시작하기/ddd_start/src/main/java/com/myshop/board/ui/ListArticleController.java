package com.myshop.board.ui;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.myshop.board.application.ListArticleService;
import com.myshop.board.domain.Article;
import com.myshop.board.query.dto.ArticleItem;

@Controller
public class ListArticleController {

	private final ListArticleService listArticleService;

	public ListArticleController(ListArticleService listArticleService) {
		this.listArticleService = listArticleService;
	}

	@GetMapping("/articles")
	public String list(ModelMap model) {
		List<Article> articles = listArticleService.getAllArticles();
		List<ArticleItem> items = articles.stream()
			.map(article -> new ArticleItem(article.getId(), article.getTitle(), article.getContent().getContent()))
			.toList();
		model.addAttribute("articles", items);
		return "articles/list";
	}
}
