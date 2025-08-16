package com.myshop.catalog.infrastructure.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductIdGenerator;
import com.myshop.catalog.domain.product.ProductRepository;

class JpaProductRepositoryTest {

	@Test
	void shouldReturnNextProductId() {
		SpringDataJpaProductRepository springDataJpaProductRepository = Mockito.mock(
			SpringDataJpaProductRepository.class);
		ProductIdGenerator generator = Mockito.mock(ProductIdGenerator.class);
		String productIdValue = "e5699056-daf5-4f52-982f-6dd63297a03b";
		BDDMockito.given(generator.generate())
			.willReturn(new ProductId(productIdValue));
		ProductRepository repository = new JpaProductRepository(springDataJpaProductRepository, generator);

		ProductId productId = repository.nextId();

		Assertions.assertThat(repository).isNotNull();
		Assertions.assertThat(productId).isEqualTo(new ProductId(productIdValue));
	}
}
