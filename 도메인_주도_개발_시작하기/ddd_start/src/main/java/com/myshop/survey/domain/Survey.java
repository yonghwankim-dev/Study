package com.myshop.survey.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Survey {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;
	@Column(name = "title", nullable = false)
	private String title;

	protected Survey() {
	}

	public Survey(String title) {
		setTitle(title);
	}

	private void setTitle(String title) {
		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("invalid title");
		}
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
}
