package com.myshop.store.domain;

import java.util.Collections;
import java.util.Set;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "store")
public class Store {
	@EmbeddedId
	private StoreId storeId;

	protected Store() {
	}

	public Store(StoreId storeId) {
		this.storeId = storeId;
	}

	public StoreId getStoreId() {
		return storeId;
	}

	public Product createProduct(ProductId newProductId) {
		if (isBlocked()) {
			throw new IllegalStateException("Store is blocked, cannot create new product");
		}
		Set<CategoryId> categoryIds = Collections.emptySet();
		return new Product(newProductId, categoryIds, storeId);
	}

	private boolean isBlocked() {
		return false;
	}
}
