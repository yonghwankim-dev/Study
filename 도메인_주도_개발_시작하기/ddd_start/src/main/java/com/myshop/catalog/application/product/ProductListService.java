package com.myshop.catalog.application.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.category.CategoryRepository;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductRepository;

public class ProductListService {

	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;


	public Page<Product> getProductOfCategory(Long categoryId, int page, int size){
		Category category = categoryRepository.findById(new CategoryId(categoryId));
		checkCategory(category);
		List<Product> products = productRepository.findByCategoryId(category.getId(), page, size);
		int totalCount = productRepository.countsByCategoryId(category.getId());
		return new PageImpl<>(products, PageRequest.of(page - 1, size), totalCount);
	}

	private void checkCategory(Category category) {

	}
}
