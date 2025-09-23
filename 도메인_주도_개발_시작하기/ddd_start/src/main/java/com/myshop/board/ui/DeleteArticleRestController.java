package com.myshop.board.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.board.application.DeleteArticleService;
import com.myshop.member.query.dto.MemberAuthentication;

@RestController
public class DeleteArticleRestController {

	private final DeleteArticleService service;

	public DeleteArticleRestController(DeleteArticleService service) {
		this.service = service;
	}

	@PostMapping("/articles/delete")
	public ResponseEntity<Void> deleteArticle(@RequestParam("id") Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuthentication = (MemberAuthentication)authentication.getPrincipal();
		String memberId = memberAuthentication.getMemberId();
		service.delete(memberId, id);
		return ResponseEntity.ok().build();
	}
}
