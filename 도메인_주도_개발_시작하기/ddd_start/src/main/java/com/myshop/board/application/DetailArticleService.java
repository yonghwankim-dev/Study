package com.myshop.board.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;
import com.myshop.board.query.dto.ArticleAndLockId;
import com.myshop.lock.LockManager;
import com.myshop.lock.domain.LockId;

@Service
public class DetailArticleService {

	private final ArticleRepository repository;
	private final LockManager lockManager;

	public DetailArticleService(ArticleRepository repository, LockManager lockManager) {
		this.repository = repository;
		this.lockManager = lockManager;
	}

	@Transactional
	public Article getArticle(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public ArticleAndLockId getArticleAndLockId(Long id) {
		// 1. 오프라인 선점 잠금 시도
		LockId lockId = lockManager.tryLock("article", id.toString());
		// 2. 기능 실행
		Article article = repository.findById(id);
		return new ArticleAndLockId(article, lockId);
	}
}
