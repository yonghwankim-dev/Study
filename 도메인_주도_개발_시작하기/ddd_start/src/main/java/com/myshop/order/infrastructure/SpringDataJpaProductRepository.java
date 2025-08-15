package com.myshop.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.order.domain.Product;
import com.myshop.order.domain.ProductId;

public interface SpringDataJpaProductRepository extends JpaRepository<Product, ProductId> {
}
