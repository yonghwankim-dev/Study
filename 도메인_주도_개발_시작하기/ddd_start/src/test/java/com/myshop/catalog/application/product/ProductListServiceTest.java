package com.myshop.catalog.application.product;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import com.myshop.catalog.domain.product.Product;

class ProductListServiceTest {

	@Test
	void canCreated(){
		ProductListService service = new ProductListService();
		assertNotNull(service);
	}

	@Test
	void shouldReturnPagedProductList(){
		ProductListService service = new ProductListService();
		Long categoryId = 1L;
		int page = 1;
		int size = 5;

		Page<Product> products = service.getProductOfCategory(categoryId, page, size);

		Assertions.assertThat(products).isNotNull();
	}
}
