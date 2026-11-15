package com.nemo.unit_test.ch06.ex01;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PriceEngineTests {

	static class Product{
		private final String name;

		public Product(String name) {
			this.name = name;
		}
	}

	static class PriceEngine{

		public double calculateDiscount(Product... products) {
			double discount = products.length * 0.01;
			return Math.min(discount, 0.2);
		}
	}

	/**
	 * 출력 기반 테스트
	 */
	@Test
	void discount_of_two_products(){
		// given
		Product product1 = new Product("Hand wash");
		Product product2 = new Product("Shampoo");
		PriceEngine sut = new PriceEngine();
		// when
		double discount = sut.calculateDiscount(product1, product2);
		// then
		Assertions.assertThat(discount).isEqualTo(0.02);
	}

}
