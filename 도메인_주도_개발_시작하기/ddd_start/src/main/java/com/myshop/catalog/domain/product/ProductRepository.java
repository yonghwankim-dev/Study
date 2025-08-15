package com.myshop.catalog.domain.product;

public interface ProductRepository {

	Product findById(ProductId id);

	void save(Product product);

	void delete(Product product);
}
