package com.myshop.catalog.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductInfo {
	@Column(name = "product_name")
	private String productName;

	protected ProductInfo() {
	}

	public ProductInfo(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}
}
