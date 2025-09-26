package com.myshop.springconfig.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.myshop.catalog.application.product.NewProductRequest;
import com.myshop.catalog.application.product.RegisterProductService;
import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.category.CategoryRepository;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.member.application.JoinService;
import com.myshop.member.domain.MemberId;
import com.myshop.member.query.dto.JoinRequest;
import com.myshop.order.command.application.OrderProduct;
import com.myshop.order.command.application.PlaceOrderService;
import com.myshop.order.query.dto.OrderRequest;
import com.myshop.store.application.RegisterStoreService;
import com.myshop.store.domain.StoreId;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	private final JoinService joinService;
	private final RegisterProductService registerProductService;
	private final RegisterStoreService registerStoreService;
	private final CategoryRepository categoryRepository;

	private final PlaceOrderService placeOrderService;
	private boolean alreadySetup = false;

	public SetupDataLoader(JoinService joinService, RegisterProductService registerProductService,
		RegisterStoreService registerStoreService, CategoryRepository categoryRepository,
		PlaceOrderService placeOrderService) {
		this.joinService = joinService;
		this.registerProductService = registerProductService;
		this.registerStoreService = registerStoreService;
		this.categoryRepository = categoryRepository;
		this.placeOrderService = placeOrderService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		MemberId memberId = setupMember();
		setupStore();
		Category category = setupCategory();
		List<ProductId> productIds = setupProducts(category.getId().getValue());
		ProductId productId = productIds.get(0);
		setupOrder(memberId, productId);
		alreadySetup = true;
	}

	private Category setupCategory() {
		Category category = new Category(new CategoryId(1L), "Electronics");
		categoryRepository.save(category);
		return category;
	}

	private MemberId setupMember() {
		String name = "홍길동";
		String address1 = "서울시 강남구 역삼동";
		String address2 = "삼성빌딩 101호";
		String zipCode = "12345";
		String email = "hong1234@gmail.com";
		String password = "hong1234@";
		JoinRequest joinRequest = new JoinRequest(name, address1, address2, zipCode, email, password);
		return joinService.join(joinRequest);
	}

	private void setupStore() {
		registerStoreService.register("store-1");
	}

	private List<ProductId> setupProducts(Long categoryId) {
		List<ProductId> productIds = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			StoreId storeId = new StoreId("store-1");
			String productName = "Product " + i;
			int price = 10_000;
			String detail = "Product " + i + " detail description";
			NewProductRequest request = new NewProductRequest(storeId, categoryId, productName, price, detail);
			ProductId productId = registerProductService.registerNewProduct(request);
			productIds.add(productId);
		}
		return productIds;
	}

	private void setupOrder(MemberId memberId, ProductId productId) {
		List<OrderProduct> orderProducts = new ArrayList<>();
		orderProducts.add(new OrderProduct(productId.getId(), 2));
		String receiverName = "홍길동";
		String receiverPhone = "010-1234-5678";
		String message = "빠른 배송 부탁드립니다.";
		String address1 = "서울시 강남구 역삼동";
		String address2 = "삼성빌딩 101호";
		String zipCode = "12345";
		OrderRequest orderRequest = new OrderRequest(
			orderProducts,
			memberId.getId(),
			receiverName,
			receiverPhone,
			message,
			address1,
			address2,
			zipCode
		);
		placeOrderService.placeOrder(orderRequest);
	}
}
