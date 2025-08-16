package com.myshop.catalog.infrastructure.category;

import org.springframework.stereotype.Repository;

import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.category.CategoryRepository;

@Repository
public class JpaCategoryRepository implements CategoryRepository {

	private final SpringDataJpaCategoryRepository springDataJpaCategoryRepository;

	public JpaCategoryRepository(SpringDataJpaCategoryRepository springDataJpaCategoryRepository) {
		this.springDataJpaCategoryRepository = springDataJpaCategoryRepository;
	}

	@Override
	public Category findById(CategoryId id) {
		return springDataJpaCategoryRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
	}

	@Override
	public void save(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Category cannot be null");
		}
		springDataJpaCategoryRepository.save(category);
	}

	@Override
	public void delete(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Category cannot be null");
		}
		springDataJpaCategoryRepository.delete(category);
	}

	@Override
	public void deleteAll() {
		springDataJpaCategoryRepository.deleteAll();
	}
}
