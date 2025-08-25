package com.myshop.catalog.domain.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.store.domain.StoreId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	@EmbeddedId
	private ProductId id;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"))
	private Set<CategoryId> categoryIds;

	@Column(name = "store_id")
	private StoreId storeId;

	@Embedded
	private ProductInfo productInfo;

	@OneToMany(
		cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
		orphanRemoval = true
	)
	@JoinColumn(name = "product_id")
	@OrderColumn(name = "list_idx")
	private List<Image> images = new ArrayList<>();

	protected Product() {
	}

	public Product(ProductId id, Set<CategoryId> categoryIds, StoreId storeId, ProductInfo productInfo,
		List<Image> images) {
		this.id = id;
		this.categoryIds = categoryIds;
		this.storeId = storeId;
		this.productInfo = productInfo;
		this.images.addAll(images);
	}

	public void changeImages(List<Image> newImages) {
		images.clear();
		images.addAll(newImages);
	}

	public ProductId getId() {
		return id;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public List<Image> getImages() {
		return Collections.unmodifiableList(images);
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
