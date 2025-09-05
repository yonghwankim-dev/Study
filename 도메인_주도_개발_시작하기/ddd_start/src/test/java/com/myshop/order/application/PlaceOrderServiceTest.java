package com.myshop.order.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.ExternalImage;
import com.myshop.catalog.domain.product.Image;
import com.myshop.catalog.domain.product.InternalImage;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductInfo;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.common.model.Money;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.domain.Password;
import com.myshop.order.domain.Address;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;
import com.myshop.order.domain.Receiver;
import com.myshop.order.domain.ShippingInfo;
import com.myshop.order.query.dto.OrderRequest;
import com.myshop.store.domain.StoreId;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlaceOrderServiceTest {

	@Autowired
	private PlaceOrderService service;

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MemberRepository memberRepository;

	private Member createMember() {
		MemberId memberId = new MemberId("member-1");
		String name = "홍길동";
		Address address = new Address("서울시 강남구 역삼동", "101동 202호", "12345");
		Password password = new Password("password");
		return new Member(memberId, name, address, password);
	}

	private Product createProduct1() {
		ProductId productId = new ProductId("product-1");
		Set<CategoryId> categoryIds = Set.of(new CategoryId(1L), new CategoryId(2L));
		StoreId storeId = new StoreId("store-1");
		ProductInfo productInfo = new ProductInfo("Java Book", new Money(10_000), "Java Programming Book");
		List<Image> images = List.of(new InternalImage("image1.jpg"), new ExternalImage("image2.jpg"));
		return new Product(productId, categoryIds, storeId, productInfo, images);
	}

	private Product createProduct2() {
		ProductId productId = new ProductId("product-2");
		Set<CategoryId> categoryIds = Set.of(new CategoryId(1L), new CategoryId(3L));
		StoreId storeId = new StoreId("store-1");
		ProductInfo productInfo = new ProductInfo("C++ Book", new Money(15_000), "C++ Programming Book");
		List<Image> images = List.of(new InternalImage("image3.jpg"), new ExternalImage("image4.jpg"));
		return new Product(productId, categoryIds, storeId, productInfo, images);
	}

	@BeforeEach
	void setUp() {
		memberRepository.save(createMember());
		productRepository.save(createProduct1());
		productRepository.save(createProduct2());
	}

	@AfterEach
	void tearDown() {
		repository.deleteAll();
		productRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void placeOrder() {
		Receiver receiver = new Receiver("홍길동", "010-1234-5678");
		String message = "문 앞에 놓아주세요.";
		Address address = new Address("서울시 강남구 역삼동", "101동 202호", "12345");
		ShippingInfo shippingInfo = new ShippingInfo(receiver, message, address);
		List<OrderProduct> orderProducts = new ArrayList<>();
		String productId1 = "product-1";
		String productId2 = "product-2";
		orderProducts.add(new OrderProduct(productId1, 2));
		orderProducts.add(new OrderProduct(productId2, 3));
		MemberId memberId = new MemberId("member-1");
		OrderRequest request = new OrderRequest(orderProducts, memberId, shippingInfo);

		OrderNo orderNo = service.placeOrder(request);

		Assertions.assertThat(orderNo).isNotNull();
		Assertions.assertThat(repository.findById(orderNo)).isPresent();
	}
}
