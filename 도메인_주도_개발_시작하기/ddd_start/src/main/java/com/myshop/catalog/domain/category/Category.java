package com.myshop.catalog.domain.category;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.myshop.catalog.domain.product.Product;

public class Category {

	private CategoryId id;
	private Set<Product> products;

	public Category(Set<Product> products) {
		this.products = products;
	}

	public List<Product> getProducts(int page, int size){
		List<Product> sortedProducts = sortById(products);
		return sortedProducts.subList((page - 1) * size, page * size);
	}

	private List<Product> sortById(Set<Product> products) {
		return products.stream()
				.sorted(Comparator.comparing(p -> p.getId().getId()))
				.toList();
	}

	public CategoryId getId() {
		return id;
	}
}
