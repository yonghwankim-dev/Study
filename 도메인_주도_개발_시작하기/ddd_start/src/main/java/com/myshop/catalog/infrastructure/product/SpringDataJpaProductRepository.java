package com.myshop.catalog.infrastructure.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;

public interface SpringDataJpaProductRepository extends JpaRepository<Product, ProductId> {
	@Query("SELECT p FROM Product p "
		+ "WHERE :id MEMBER OF p.categoryIds "
		+ "ORDER BY p.id.id DESC")
	List<Product> findByCategoryId(@Param("id") CategoryId id, Pageable pageable);

	@Query("SELECT COUNT(p) FROM Product p WHERE :id MEMBER OF p.categoryIds")
	int countsByCategoryId(@Param("id") CategoryId id);
}
