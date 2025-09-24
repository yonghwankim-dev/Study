package com.myshop.catalog.domain.product.service;

import java.util.List;

import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;

public interface ProductRecommendationService {
	List<Product> getRecommendedProducts(ProductId productId);
}
