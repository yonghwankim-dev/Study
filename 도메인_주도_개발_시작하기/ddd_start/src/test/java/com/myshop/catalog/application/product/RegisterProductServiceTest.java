package com.myshop.catalog.application.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegisterProductServiceTest {

	@Autowired
	private ProductRepository productRepository;
	private RegisterProductService service;

	@BeforeEach
	void setUp() {
		service = new RegisterProductService(productRepository);
	}

	@AfterEach
	void tearDown() {
		productRepository.deleteAll();
	}

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void shouldSaveNewProduct() {
		NewProductRequest request = new NewProductRequest();

		ProductId productId = service.registerNewProduct(request);

		Assertions.assertThat(productId).isNotNull();
		Assertions.assertThat(productId).isEqualTo(new ProductId("9000000112298"));
		Assertions.assertThat(productRepository.findById(productId)).isNotNull();
	}

}
