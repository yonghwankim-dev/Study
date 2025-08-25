package com.myshop.store.domain;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.catalog.domain.product.Image;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductInfo;
import com.myshop.common.model.Money;

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
		Money price = new Money(1000);
		String detail = "Java Programming Book";
		ProductInfo productInfo = new ProductInfo("Java Book", price, detail);
		List<Image> images = new ArrayList<>();

		Product newProduct = store.createProduct(newProductId, productInfo, images);

		Assertions.assertThat(newProduct).isNotNull();
	}
}
