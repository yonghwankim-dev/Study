package com.myshop.catalog.application.product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductListServiceTest {

	@Test
	void canCreated(){
		ProductListService service = new ProductListService();
		assertNotNull(service);
	}
}
