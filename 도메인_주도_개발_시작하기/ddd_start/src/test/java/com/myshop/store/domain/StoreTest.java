package com.myshop.store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductInfo;

class StoreTest {

	@Test
	void canCreated() {
		Store store = new Store(new StoreId("123456789"));

		Assertions.assertThat(store).isNotNull();
	}

	@Test
	void shouldReturnProduct() {
		Store store = new Store(new StoreId("123456789"));
		ProductId newProductId = new ProductId("9000000112298");
		ProductInfo productInfo = new ProductInfo("Java Book");

		Product newProduct = store.createProduct(newProductId, productInfo);

		Assertions.assertThat(newProduct).isNotNull();
	}
}
