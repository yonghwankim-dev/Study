package com.myshop.board.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myshop.board.application.DetailArticleService;
import com.myshop.board.domain.Article;
import com.myshop.board.query.dto.ArticleAndLockId;
import com.myshop.board.query.dto.ArticleItem;
import com.myshop.lock.error.AlreadyLockedException;

@Controller
public class EditArticleController {

	private final DetailArticleService service;

	public EditArticleController(DetailArticleService service) {
		this.service = service;
	}

	@GetMapping("/articles/edit")
	public String editForm(@RequestParam("id") Long id, ModelMap model) {
		ArticleAndLockId articleAndLockId;
		try {
			articleAndLockId = service.getArticleAndLockId(id);// 잠금 선점
		} catch (AlreadyLockedException exception) {
			return "redirect:/articles/detail?id=" + id + "&error=alreadyLocked";
		}

		Article article = articleAndLockId.getArticle();
		ArticleItem item = new ArticleItem(article.getId(), article.getTitle(), article.getContent().getContent());
		model.addAttribute("article", item);
		model.addAttribute("lockId", articleAndLockId.getLockId());
		return "articles/editForm";
	}
}
