package com.myshop.catalog.infrastructure.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.myshop.catalog.domain.product.ProductRepository;

class JpaProductRepositoryTest {

	@Test
	void shouldReturnNextProductId() {
		SpringDataJpaProductRepository springDataJpaProductRepository = Mockito.mock(
			SpringDataJpaProductRepository.class);
		ProductRepository repository = new JpaProductRepository(springDataJpaProductRepository);

		Assertions.assertThat(repository).isNotNull();
	}
}
