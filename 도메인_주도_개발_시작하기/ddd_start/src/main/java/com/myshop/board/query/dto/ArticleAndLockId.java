package com.myshop.board.query.dto;

import com.myshop.board.domain.Article;
import com.myshop.lock.domain.LockId;

public class ArticleAndLockId {
	private Article article;
	private LockId lockId;

	public ArticleAndLockId() {
	}

	public ArticleAndLockId(Article article, LockId lockId) {
		this.article = article;
		this.lockId = lockId;
	}

	public Article getArticle() {
		return article;
	}

	public LockId getLockId() {
		return lockId;
	}
}
