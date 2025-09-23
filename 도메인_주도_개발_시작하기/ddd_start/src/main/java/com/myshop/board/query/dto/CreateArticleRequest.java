package com.myshop.board.query.dto;

public class CreateArticleRequest {
	private String title;
	private String content;
	private String authorId;

	public CreateArticleRequest() {
	}

	public CreateArticleRequest(String title, String content, String authorId) {
		this.title = title;
		this.content = content;
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getAuthorId() {
		return authorId;
	}
}
