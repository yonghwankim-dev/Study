package com.myshop.store.domain;

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
}
