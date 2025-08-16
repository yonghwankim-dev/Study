package com.myshop.catalog.infrastructure.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;

class JpaProductRepositoryTest {

	@Test
	void shouldReturnNextProductId() {
		SpringDataJpaProductRepository springDataJpaProductRepository = Mockito.mock(
			SpringDataJpaProductRepository.class);
		ProductRepository repository = new JpaProductRepository(springDataJpaProductRepository);

		ProductId productId = repository.nextId();

		Assertions.assertThat(repository).isNotNull();
		Assertions.assertThat(productId).isEqualTo(new ProductId("9000000112298"));
	}
}
