package com.myshop.catalog.infrastructure.product;

import java.util.List;

import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.service.ProductRecommendationService;

public class RecSystemClient implements ProductRecommendationService {
	@Override
	public List<Product> getRecommendedProducts(ProductId productId) {
		return null;
	}
}
