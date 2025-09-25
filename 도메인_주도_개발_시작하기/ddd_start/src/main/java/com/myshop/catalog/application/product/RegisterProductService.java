package com.myshop.catalog.application.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.Image;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductInfo;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.common.model.Money;
import com.myshop.store.domain.Store;
import com.myshop.store.domain.StoreRepository;

@Service
public class RegisterProductService {

	private final ProductRepository productRepository;
	private final StoreRepository storeRepository;

	public RegisterProductService(ProductRepository productRepository, StoreRepository storeRepository) {
		this.productRepository = productRepository;
		this.storeRepository = storeRepository;
	}

	@Transactional
	public ProductId registerNewProduct(NewProductRequest request) {
		Store store = storeRepository.findById(request.getStoreId());
		checkNull(store);
		ProductId id = productRepository.nextId();
		Money price = new Money(request.getPrice());
		String detail = request.getDetail();
		ProductInfo productInfo = new ProductInfo(request.getProductName(), price, detail);
		List<Image> images = new ArrayList<>();
		Product product = store.createProduct(id, productInfo, images);

		product.addCategoryId(new CategoryId(request.getCategoryId()));
		productRepository.save(product);
		return id;
	}

	private void checkNull(Store store) {
		if (store == null) {
			throw new IllegalArgumentException("Store cannot be null");
		}
	}
}
