package com.myshop.catalog.domain.product;

import java.util.Objects;
import java.util.Set;

import com.myshop.catalog.domain.category.CategoryId;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	@EmbeddedId
	private ProductId id;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"))
	private Set<CategoryId> categoryIds;

	protected Product() {
	}

	public Product(ProductId id, Set<CategoryId> categoryIds) {
		this.id = id;
		this.categoryIds = categoryIds;
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
