package com.myshop.catalog.domain.category;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CategoryId implements Serializable {
	@Column(name = "category_id")
	private Long id;

	protected CategoryId() {
	}

	public CategoryId(Long id) {
		this.id = id;
	}
}
