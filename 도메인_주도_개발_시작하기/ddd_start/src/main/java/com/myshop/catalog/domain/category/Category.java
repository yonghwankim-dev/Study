package com.myshop.catalog.domain.category;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Category {
	@EmbeddedId
	private CategoryId id;
	@Column(name = "name")
	private String name;

	protected Category() {
	}

	public Category(CategoryId id, String name) {
		this.id = id;
		this.name = name;
	}

	public CategoryId getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
