package com.myshop.catalog.infrastructure.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;

public interface SpringDataJpaProductRepository extends JpaRepository<Product, ProductId> {
}
