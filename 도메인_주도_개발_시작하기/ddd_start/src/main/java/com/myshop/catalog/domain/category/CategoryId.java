package com.myshop.catalog.domain.category;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CategoryId implements Serializable {
	@Column(name = "category_id")
	private Long value;

	protected CategoryId() {
	}

	public CategoryId(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return value;
	}
}
