package com.myshop.catalog.infrastructure.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryId;

public interface SpringDataJpaCategoryRepository extends JpaRepository<Category, CategoryId> {

}
