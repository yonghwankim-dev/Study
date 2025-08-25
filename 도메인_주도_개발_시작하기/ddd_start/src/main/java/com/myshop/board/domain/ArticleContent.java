package com.myshop.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ArticleContent {
	@Column(name = "content")
	private String content;
	@Column(name = "content_type")
	private String contentType;

	protected ArticleContent() {
	}

	public ArticleContent(String content, String contentType) {
		this.content = content;
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public String getContentType() {
		return contentType;
	}
}
