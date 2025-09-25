package com.myshop.catalog.query.product.dto;

public class ProductDto {
	private String productId;
	private String productName;

	public ProductDto() {
	}

	public ProductDto(String productId, String productName) {
		this.productId = productId;
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}
}
