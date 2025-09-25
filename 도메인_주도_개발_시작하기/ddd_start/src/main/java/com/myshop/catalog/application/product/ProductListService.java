package com.myshop.catalog.application.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.category.CategoryRepository;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductRepository;

@Service
public class ProductListService {

	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;

	public ProductListService(CategoryRepository categoryRepository, ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}

	public Page<Product> getProductOfCategory(Long categoryId, int page, int size) {
		Category category = categoryRepository.findById(new CategoryId(categoryId));
		List<Product> products = productRepository.findByCategoryId(category.getId(), page, size);
		int totalCount = productRepository.countsByCategoryId(category.getId());
		return new PageImpl<>(products, PageRequest.of(page - 1, size), totalCount);
	}
}
