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
import com.myshop.store.domain.Store;
import com.myshop.store.domain.StoreId;
import com.myshop.store.domain.StoreRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegisterProductServiceTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StoreRepository storeRepository;
	private RegisterProductService service;
	private StoreId storeId;

	@BeforeEach
	void setUp() {
		service = new RegisterProductService(productRepository, storeRepository);
		storeId = saveStore();
	}

	@AfterEach
	void tearDown() {
		productRepository.deleteAll();
		storeRepository.deleteAll();
	}

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void shouldSaveNewProduct() {
		NewProductRequest request = new NewProductRequest(storeId, "Java Book");

		ProductId productId = service.registerNewProduct(request);

		Assertions.assertThat(productId).isNotNull();
		Assertions.assertThat(productRepository.findById(productId)).isNotNull();
	}

	private StoreId saveStore() {
		StoreId newStoreId = new StoreId("123456789");
		Store store = new Store(newStoreId);
		storeRepository.save(store);
		return newStoreId;
	}
}
