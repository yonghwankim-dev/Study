package com.myshop.catalog.infrastructure.product;

import java.util.UUID;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductIdGenerator;

public class UuidProductIdGenerator implements ProductIdGenerator {
	@Override
	public ProductId generate() {
		return new ProductId(UUID.randomUUID().toString());
	}
}
