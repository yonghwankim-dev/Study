package com.myshop.catalog.infrastructure.product;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductIdGenerator;

@Component
public class UuidProductIdGenerator implements ProductIdGenerator {
	@Override
	public ProductId generate() {
		return new ProductId(UUID.randomUUID().toString());
	}
}
