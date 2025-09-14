package com.myshop.board.application;

import org.springframework.stereotype.Service;

import com.myshop.board.domain.Article;

@Service
public class PermissionService {
	public void checkDeletePermission(String userId, Article article) {
		if (!article.getAuthorId().equals(userId)) {
			throw new IllegalStateException("User does not have permission to delete this article.");
		}
	}
}
