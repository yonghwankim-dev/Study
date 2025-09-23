package com.myshop.board.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;
import com.myshop.board.query.dto.UpdateArticleRequest;
import com.myshop.lock.LockManager;
import com.myshop.lock.domain.LockId;

@Service
public class UpdateArticleService {

	private final ArticleRepository repository;
	private final LockManager lockManager;

	public UpdateArticleService(ArticleRepository repository, LockManager lockManager) {
		this.repository = repository;
		this.lockManager = lockManager;
	}

	@Transactional
	public void update(UpdateArticleRequest request) {
		// 1. 잠금 선점 확인
		LockId lockId = new LockId(request.getLockId());
		lockManager.checkLock(lockId);
		// 2. 기능 실행
		Article article = repository.findById(request.getId());
		article.changeTitle(request.getTitle());
		article.changeContent(request.getContent());
		// 3. 잠금 해제
		lockManager.releaseLock(lockId);
	}
}
