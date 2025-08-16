package com.myshop.catalog.application.product;

import java.util.Collections;
import java.util.Set;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.store.domain.Store;
import com.myshop.store.domain.StoreRepository;

public class RegisterProductService {

	private final ProductRepository productRepository;
	private final StoreRepository storeRepository;

	public RegisterProductService(ProductRepository productRepository, StoreRepository storeRepository) {
		this.productRepository = productRepository;
		this.storeRepository = storeRepository;
	}

	public ProductId registerNewProduct(NewProductRequest request) {
		Store store = storeRepository.findById(request.getStoreId());
		checkNull(store);
		ProductId id = productRepository.nextId();
		Set<CategoryId> categoryIds = Collections.emptySet();
		Product product = new Product(id, categoryIds, store.getStoreId());
		productRepository.save(product);
		return id;
	}

	private void checkNull(Store store) {
		if (store == null) {
			throw new IllegalArgumentException("Store cannot be null");
		}
	}
}
