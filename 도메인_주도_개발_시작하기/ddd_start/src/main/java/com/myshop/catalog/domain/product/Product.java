package com.myshop.catalog.domain.product;

import java.util.Objects;

import com.myshop.catalog.domain.category.CategoryId;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	@EmbeddedId
	private ProductId id;

	@Embedded
	private CategoryId categoryId;

	protected Product() {
	}

	public Product(ProductId id, CategoryId categoryId) {
		this.id = id;
		this.categoryId = categoryId;
	}

	public ProductId getId() {
		return id;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Product product = (Product)object;
		return Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
