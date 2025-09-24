package com.myshop.catalog.infrastructure.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.catalog.domain.product.service.ProductRecommendationService;

class RecSystemClientTest {

	@Test
	void canCreated() {
		ProductRecommendationService service = new RecSystemClient();

		Assertions.assertThat(service).isNotNull();
	}
}
