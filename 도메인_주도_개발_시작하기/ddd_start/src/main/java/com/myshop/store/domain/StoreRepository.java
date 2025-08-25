package com.myshop.store.domain;

public interface StoreRepository {
	Store findById(StoreId id);

	void save(Store store);

	void delete(Store store);

	void deleteAll();
}
