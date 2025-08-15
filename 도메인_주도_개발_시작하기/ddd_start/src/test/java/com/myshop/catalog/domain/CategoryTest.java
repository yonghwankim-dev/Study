package com.myshop.catalog.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;

class CategoryTest {

	@Test
	void shouldReturnSortedProductList(){
		Set<Product> products = new HashSet<>();
		for (int i = 1; i < 20; i++){
			products.add(new Product(new ProductId(String.valueOf(i))));
		}
		Category category = new Category(products);
		int page = 1;
		int size = 5;

		List<Product> sortedProducts = category.getProducts(page, size);

		assertEquals(5, sortedProducts.size());
		assertEquals(new ProductId("1"), sortedProducts.get(0).getId());
		assertEquals(new ProductId("10"), sortedProducts.get(1).getId());
		assertEquals(new ProductId("11"), sortedProducts.get(2).getId());
		assertEquals(new ProductId("12"), sortedProducts.get(3).getId());
		assertEquals(new ProductId("13"), sortedProducts.get(4).getId());
	}

}
