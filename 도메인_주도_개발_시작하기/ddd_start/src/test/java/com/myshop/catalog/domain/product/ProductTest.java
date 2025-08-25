package com.myshop.catalog.domain.product;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.FixedDomainFactory;

class ProductTest {
	@Test
	void canCreated() {
		Product product = FixedDomainFactory.createProduct();
		Assertions.assertThat(product).isNotNull();
	}

	@Test
	void shouldChangeImages() {
		Product product = FixedDomainFactory.createProduct();
		List<Image> newImages = List.of(
			new InternalImage("image1.jpg"),
			new ExternalImage("http://example.com/image2.jpg"),
			new ExternalImage("http://example.com/image3.jpg")
		);

		product.changeImages(newImages);

		Assertions.assertThat(product.getImages())
			.hasSize(3)
			.containsExactlyElementsOf(newImages);
	}
}
