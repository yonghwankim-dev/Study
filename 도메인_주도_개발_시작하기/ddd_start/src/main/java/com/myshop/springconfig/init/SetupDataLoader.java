package com.myshop.springconfig.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.myshop.catalog.application.product.NewProductRequest;
import com.myshop.catalog.application.product.RegisterProductService;
import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.category.CategoryRepository;
import com.myshop.member.application.JoinService;
import com.myshop.member.query.dto.JoinRequest;
import com.myshop.store.application.RegisterStoreService;
import com.myshop.store.domain.StoreId;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	private final JoinService joinService;
	private final RegisterProductService registerProductService;
	private final RegisterStoreService registerStoreService;
	private final CategoryRepository categoryRepository;
	private boolean alreadySetup = false;

	public SetupDataLoader(JoinService joinService, RegisterProductService registerProductService,
		RegisterStoreService registerStoreService, CategoryRepository categoryRepository) {
		this.joinService = joinService;
		this.registerProductService = registerProductService;
		this.registerStoreService = registerStoreService;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		setupMember();
		setupStore();
		Category category = setupCategory();
		setupProducts(category.getId().getValue());
		alreadySetup = true;
	}

	private Category setupCategory() {
		Category category = new Category(new CategoryId(1L), "Electronics");
		categoryRepository.save(category);
		return category;
	}

	private void setupMember() {
		String name = "홍길동";
		String address1 = "서울시 강남구 역삼동";
		String address2 = "삼성빌딩 101호";
		String zipCode = "12345";
		String email = "hong1234@gmail.com";
		String password = "hong1234@";
		JoinRequest joinRequest = new JoinRequest(name, address1, address2, zipCode, email, password);
		joinService.join(joinRequest);
	}

	private void setupStore() {
		registerStoreService.register("store-1");
	}

	private void setupProducts(Long categoryId) {
		for (int i = 1; i <= 5; i++) {
			StoreId storeId = new StoreId("store-1");
			String productName = "Product " + i;
			int price = 10_000;
			String detail = "Product " + i + " detail description";
			NewProductRequest request = new NewProductRequest(storeId, categoryId, productName, price, detail);
			registerProductService.registerNewProduct(request);
		}
	}
}
