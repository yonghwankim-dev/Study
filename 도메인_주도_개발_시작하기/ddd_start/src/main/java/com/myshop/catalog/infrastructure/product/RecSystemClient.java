package com.myshop.catalog.infrastructure.product;

import java.util.Collections;
import java.util.List;

import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.catalog.domain.product.service.ProductRecommendationService;
import com.myshop.catalog.query.product.dto.RecommendationItem;

public class RecSystemClient implements ProductRecommendationService {

	private final ProductRepository productRepository;
	private final ExternalRecClient externalRecClient;

	public RecSystemClient(ProductRepository productRepository, ExternalRecClient externalRecClient) {
		this.productRepository = productRepository;
		this.externalRecClient = externalRecClient;
	}

	@Override
	public List<Product> getRecommendedProducts(ProductId productId) {
		if (productId == null) {
			return Collections.emptyList();
		}
		List<RecommendationItem> items = getRecItems(productId.getId());
		return toProducts(items);
	}

	private List<RecommendationItem> getRecItems(String itemId) {
		// externalRecClient는 외부 추천 시스템을 위한 클라이언트
		return externalRecClient.getRecs(itemId);
	}

	private List<Product> toProducts(List<RecommendationItem> items) {
		return items.stream()
			.map(item -> toProductId(item.getItemId()))
			.map(prodId -> productRepository.findById(prodId))
			.toList();
	}

	private ProductId toProductId(String itemId) {
		return new ProductId(itemId);
	}
}
