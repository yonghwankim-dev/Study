package com.myshop.order.domain;

public interface ProductRepository {

	Product findById(ProductId id);

	void save(Product product);

	void delete(Product product);
}
