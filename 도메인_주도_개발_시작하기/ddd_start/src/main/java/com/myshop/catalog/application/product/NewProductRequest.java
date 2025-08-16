package com.myshop.catalog.application.product;

import com.myshop.store.domain.StoreId;

public class NewProductRequest {
	private StoreId storeId;

	public NewProductRequest(StoreId storeId) {
		this.storeId = storeId;
	}

	public StoreId getStoreId() {
		return storeId;
	}
}
