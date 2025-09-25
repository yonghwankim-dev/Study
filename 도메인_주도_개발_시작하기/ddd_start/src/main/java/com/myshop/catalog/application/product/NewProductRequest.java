package com.myshop.catalog.application.product;

import com.myshop.store.domain.StoreId;

public class NewProductRequest {
	private StoreId storeId;
	private Long categoryId;
	private String productName;
	private int price;
	private String detail;

	public NewProductRequest(StoreId storeId, Long categoryId, String productName, int price, String detail) {
		this.storeId = storeId;
		this.categoryId = categoryId;
		this.productName = productName;
		this.price = price;
		this.detail = detail;
	}

	public StoreId getStoreId() {
		return storeId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public int getPrice() {
		return price;
	}

	public String getDetail() {
		return detail;
	}
}
