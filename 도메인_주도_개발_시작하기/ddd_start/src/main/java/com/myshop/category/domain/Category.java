package com.myshop.category.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.myshop.order.domain.Product;

public class Category {

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
}
