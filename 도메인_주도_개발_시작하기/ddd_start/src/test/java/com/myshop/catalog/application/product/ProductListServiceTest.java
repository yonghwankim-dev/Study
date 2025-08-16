package com.myshop.catalog.application.product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.category.CategoryRepository;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.store.domain.StoreId;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductListServiceTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;
	private ProductListService service;

	@BeforeEach
	void setUp() {
		service = new ProductListService(categoryRepository, productRepository);

		CategoryId categoryId = new CategoryId(1L);
		String categoryName = "Book";
		saveCategory(categoryId, categoryName);

		for (int i = 0; i < 10; i++) {
			ProductId productId = new ProductId("9000000112298" + i);
			saveProduct(productId, categoryId);
		}
	}

	private void saveProduct(ProductId productId, CategoryId categoryId) {
		StoreId storeId = new StoreId("123456789");
		Product product = new Product(productId, Set.of(categoryId), storeId);
		productRepository.save(product);
	}

	private void saveCategory(CategoryId categoryId, String categoryName) {
		Category category = new Category(categoryId, categoryName);
		categoryRepository.save(category);
	}

	@AfterEach
	void tearDown() {
		productRepository.deleteAll();
		categoryRepository.deleteAll();
	}

	@Test
	void canCreated() {
		assertNotNull(service);
	}

	@Test
	void shouldReturnPagedProductList() {
		Long categoryId = 1L;
		int page = 1;
		int size = 5;

		Page<Product> products = service.getProductOfCategory(categoryId, page, size);

		Assertions.assertThat(products).isNotNull();
		Assertions.assertThat(products.getTotalElements()).isEqualTo(10);
		Assertions.assertThat(products.getTotalPages()).isEqualTo(2);
		Assertions.assertThat(products.getContent()).hasSize(5);
	}
}
