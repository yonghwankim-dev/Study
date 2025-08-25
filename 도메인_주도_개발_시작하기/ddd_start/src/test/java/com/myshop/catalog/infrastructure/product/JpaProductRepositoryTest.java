package com.myshop.catalog.infrastructure.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductIdGenerator;
import com.myshop.catalog.domain.product.ProductRepository;

class JpaProductRepositoryTest {

	private ProductRepository repository;
	private ProductId productId;

	@BeforeEach
	void setUp() {
		SpringDataJpaProductRepository springDataJpaProductRepository = Mockito.mock(
			SpringDataJpaProductRepository.class);
		ProductIdGenerator generator = Mockito.mock(ProductIdGenerator.class);
		productId = new ProductId("e5699056-daf5-4f52-982f-6dd63297a03b");
		BDDMockito.given(generator.generate())
			.willReturn(productId);
		repository = new JpaProductRepository(springDataJpaProductRepository, generator);
	}

	@Test
	void shouldReturnNextProductId() {
		ProductId newProductId = repository.nextId();

		assertThat(newProductId).isEqualTo(productId);
	}
}
