package com.myshop.store.infrastructure;

import org.springframework.stereotype.Repository;

import com.myshop.store.domain.Store;
import com.myshop.store.domain.StoreId;
import com.myshop.store.domain.StoreRepository;

@Repository
public class JpaStoreRepository implements StoreRepository {

	private final SpringDataJpaStoreRepository repository;

	public JpaStoreRepository(SpringDataJpaStoreRepository repository) {
		this.repository = repository;
	}

	@Override
	public Store findById(StoreId id) {
		return repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + id));
	}

	@Override
	public void save(Store store) {
		if (store == null) {
			throw new IllegalArgumentException("Store cannot be null");
		}
		repository.save(store);
	}

	@Override
	public void delete(Store store) {
		if (store == null) {
			throw new IllegalArgumentException("Store cannot be null");
		}
		repository.delete(store);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
}
