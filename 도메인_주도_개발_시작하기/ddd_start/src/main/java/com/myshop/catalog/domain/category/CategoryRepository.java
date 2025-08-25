package com.myshop.catalog.domain.category;

public interface CategoryRepository {
	Category findById(CategoryId id);

	void save(Category category);

	void delete(Category category);

	void deleteAll();
}
