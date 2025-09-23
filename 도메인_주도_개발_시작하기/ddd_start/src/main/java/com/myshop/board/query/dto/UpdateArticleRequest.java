package com.myshop.board.query.dto;

public class UpdateArticleRequest {
	private Long id;
	private String title;
	private String content;
	private String lockId;

	public UpdateArticleRequest() {
	}

	public UpdateArticleRequest(Long id, String title, String content, String lockId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.lockId = lockId;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getLockId() {
		return lockId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}
}
