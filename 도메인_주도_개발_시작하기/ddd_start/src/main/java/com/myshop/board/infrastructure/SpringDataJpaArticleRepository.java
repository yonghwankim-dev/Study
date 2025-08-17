package com.myshop.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.board.domain.Article;

public interface SpringDataJpaArticleRepository extends JpaRepository<Article, Long> {
	
}
