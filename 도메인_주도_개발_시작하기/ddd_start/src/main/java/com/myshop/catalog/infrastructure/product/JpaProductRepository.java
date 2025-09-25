package com.myshop.catalog.infrastructure.product;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductIdGenerator;
import com.myshop.catalog.domain.product.ProductRepository;

@Repository
public class JpaProductRepository implements ProductRepository {

	private final SpringDataJpaProductRepository repository;
	private final ProductIdGenerator generator;

	public JpaProductRepository(SpringDataJpaProductRepository repository, ProductIdGenerator generator) {
		this.repository = repository;
		this.generator = generator;
	}

	@Override
	public Product findById(ProductId id) {
		return repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
	}

	@Override
	public void save(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}
		repository.save(product);
	}

	@Override
	public void delete(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}
		repository.delete(product);
	}

	@Override
	public List<Product> findByCategoryId(CategoryId id, int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		return repository.findByCategoryId(id, pageable);
	}

	@Override
	public List<Product> findAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page - 1, size);
		return repository.findAll(pageRequest).getContent();
	}

	@Override
	public int countsByCategoryId(CategoryId id) {
		return repository.countsByCategoryId(id);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public ProductId nextId() {
		return generator.generate();
	}

	@Override
	public long counts() {
		return repository.count();
	}
}
