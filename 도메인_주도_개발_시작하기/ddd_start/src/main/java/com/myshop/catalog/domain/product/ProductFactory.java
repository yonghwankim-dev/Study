package com.myshop.catalog.domain.product;

import java.util.List;
import java.util.Set;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.store.domain.StoreId;

public class ProductFactory {
	public static Product create(ProductId productId, Set<CategoryId> categoryIds, StoreId storeId,
		ProductInfo productInfo, List<Image> images) {
		return new Product(productId, categoryIds, storeId, productInfo, images);
	}
}
