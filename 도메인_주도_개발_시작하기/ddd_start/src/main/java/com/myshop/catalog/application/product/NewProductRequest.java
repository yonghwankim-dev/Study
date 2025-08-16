package com.myshop.catalog.application.product;

import com.myshop.store.domain.StoreId;

public class NewProductRequest {
	private StoreId storeId;
	private String productName;

	public NewProductRequest(StoreId storeId, String productName) {
		this.storeId = storeId;
		this.productName = productName;
	}

	public StoreId getStoreId() {
		return storeId;
	}

	public String getProductName() {
		return productName;
	}
}
