package com.myshop.catalog.infrastructure.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.Image;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductInfo;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.catalog.domain.product.service.ProductRecommendationService;
import com.myshop.catalog.query.product.dto.RecommendationItem;
import com.myshop.common.model.Money;
import com.myshop.store.domain.StoreId;

class RecSystemClientTest {

	private ProductRecommendationService service;
	private ProductId productId;

	@BeforeEach
	void setUp() {
		productId = new ProductId("product-1");

		ProductRepository productRepository = Mockito.mock(ProductRepository.class);
		ExternalRecClient externalRecClient = Mockito.mock(ExternalRecClient.class);

		Product recommendProduct = createRecommendProduct();
		BDDMockito.given(productRepository.findById(recommendProduct.getId()))
			.willReturn(recommendProduct);
		RecommendationItem recItem = new RecommendationItem(recommendProduct.getId().getId());
		BDDMockito.given(externalRecClient.getRecs(productId.getId()))
			.willReturn(List.of(recItem));

		service = new RecSystemClient(productRepository, externalRecClient);
	}

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void shouldEmptyList_whenProductIdIsNull() {
		Assertions.assertThat(service.getRecommendedProducts(null)).isEmpty();
	}

	@Test
	void getRecommendedProducts() {
		List<Product> products = service.getRecommendedProducts(productId);

		Product recommendProduct = createRecommendProduct();
		Assertions.assertThat(products)
			.hasSize(1)
			.contains(recommendProduct);
	}

	private Product createRecommendProduct() {
		ProductId recommendationProductId = new ProductId("product-2");
		Set<CategoryId> categoryIds = new HashSet<>();
		StoreId storeId = new StoreId("store-1");
		ProductInfo productInfo = new ProductInfo(
			"Product 2",
			new Money(10_000),
			"Detailed info of Product 2"
		);
		List<Image> images = new ArrayList<>();
		return new Product(
			recommendationProductId,
			categoryIds,
			storeId,
			productInfo,
			images
		);
	}
}
