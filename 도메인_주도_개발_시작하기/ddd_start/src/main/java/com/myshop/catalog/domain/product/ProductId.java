package com.myshop.catalog.domain.product;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductId implements Serializable {

	@Column(name = "product_id")
	private String id;

	protected ProductId() {
	}

	public ProductId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		ProductId productId = (ProductId)object;
		return Objects.equals(id, productId.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
