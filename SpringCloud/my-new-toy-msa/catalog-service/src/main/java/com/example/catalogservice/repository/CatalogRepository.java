package com.example.catalogservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.catalogservice.jpa.CatalogEntity;

public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
	CatalogEntity findByProductId(String productId);
}
