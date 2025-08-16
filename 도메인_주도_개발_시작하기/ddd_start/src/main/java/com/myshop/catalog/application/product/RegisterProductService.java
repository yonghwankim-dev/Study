package com.myshop.catalog.application.product;

import java.util.Collections;
import java.util.Set;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;

public class RegisterProductService {

	private final ProductRepository productRepository;

	public RegisterProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public ProductId registerNewProduct(NewProductRequest request) {
		ProductId id = new ProductId("9000000112298");
		Set<CategoryId> categoryIds = Collections.emptySet();
		Product product = new Product(id, categoryIds);
		productRepository.save(product);
		return id;
	}
}
