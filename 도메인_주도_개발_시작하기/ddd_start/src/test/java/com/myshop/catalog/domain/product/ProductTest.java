package com.myshop.catalog.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;

class ProductTest {
	@Test
	void canCreated() {
		Product product = FixedDomainFactory.createProduct();
		Assertions.assertThat(product).isNotNull();
	}
}
