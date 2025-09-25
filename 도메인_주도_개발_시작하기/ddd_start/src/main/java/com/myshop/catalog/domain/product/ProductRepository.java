package com.myshop.catalog.domain.product;

import java.util.List;

import com.myshop.catalog.domain.category.CategoryId;

public interface ProductRepository {

	Product findById(ProductId id);

	List<Product> findAll(int page, int size);

	void save(Product product);

	void delete(Product product);

	List<Product> findByCategoryId(CategoryId id, int page, int size);

	int countsByCategoryId(CategoryId id);

	void deleteAll();

	ProductId nextId();

	long counts();
}
