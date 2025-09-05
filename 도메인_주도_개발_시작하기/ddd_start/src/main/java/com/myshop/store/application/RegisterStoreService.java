package com.myshop.store.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.store.domain.Store;
import com.myshop.store.domain.StoreId;
import com.myshop.store.domain.StoreRepository;

@Service
public class RegisterStoreService {

	private final StoreRepository storeRepository;

	public RegisterStoreService(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}

	@Transactional
	public void register(String id) {
		Store store = new Store(new StoreId(id));
		storeRepository.save(store);
	}
}
