package com.nemo.unit_test.ch06.ex02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class OrderTests {

	static class Product{
		private final String name;

		public Product(String name) {
			this.name = name;
		}
	}

	static class Order{

		private final List<Product> products = new ArrayList<>();

		public void addProduct(Product product) {
			this.products.add(product);
		}

		public List<Product> getProducts() {
			return Collections.unmodifiableList(this.products);
		}
	}

	@Test
	void adding_a_product_to_an_order(){
		Product product = new Product("Hand wash");
		Order sut = new Order();

		sut.addProduct(product);

		Assertions.assertThat(sut.getProducts())
			.hasSize(1)
			.containsExactly(product);
	}
}
