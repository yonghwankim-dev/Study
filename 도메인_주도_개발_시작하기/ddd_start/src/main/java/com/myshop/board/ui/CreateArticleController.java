package com.myshop.board.ui;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.myshop.member.query.dto.MemberAuthentication;

@Controller
public class CreateArticleController {

	// 글 작성 폼
	@GetMapping("/articles/new")
	public String createForm(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication principal = (MemberAuthentication)authentication.getPrincipal();
		String authorId = principal.getMemberId();
		model.addAttribute("authorId", authorId);
		return "articles/createForm";
	}
}
