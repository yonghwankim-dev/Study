package com.myshop;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.ExternalImage;
import com.myshop.catalog.domain.product.Image;
import com.myshop.catalog.domain.product.InternalImage;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductFactory;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductInfo;
import com.myshop.common.model.Money;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberGrade;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.Password;
import com.myshop.member.query.dto.MemberData;
import com.myshop.order.domain.model.Address;
import com.myshop.order.domain.model.Order;
import com.myshop.order.domain.model.OrderLine;
import com.myshop.order.domain.model.OrderNo;
import com.myshop.order.domain.model.OrderState;
import com.myshop.order.domain.model.Orderer;
import com.myshop.order.domain.model.Receiver;
import com.myshop.order.domain.model.ShippingInfo;
import com.myshop.order.query.dto.OrderSummary;
import com.myshop.store.domain.StoreId;

public class FixedDomainFactory {
	public static Order createOrder() {
		String orderId = "1234567890";
		return createOrder(orderId, "member-1");
	}

	public static Order createOrder(String orderId, String memberIdValue) {
		OrderNo orderNo = new OrderNo(orderId);
		MemberId memberId = new MemberId(memberIdValue);
		Orderer orderer = new Orderer(memberId, "홍길동");
		ProductId productId = new ProductId("9000000112298");
		Money price = new Money(1000);
		int quantity = 2;
		OrderLine orderLine = new OrderLine(productId, price, quantity);
		List<OrderLine> orderLines = List.of(orderLine);
		Receiver receiver = new Receiver("홍길동", "010-1234-5678");
		String message = "shipping message";
		Address address = new Address(
			"서울 강남구 역삼동",
			"735-17",
			"06235"
		);
		ShippingInfo shippingInfo = new ShippingInfo(receiver, message, address);
		OrderState state = OrderState.PAYMENT_WAITING;
		return new Order(orderNo, orderer, orderLines, shippingInfo, state);
	}

	public static Product createProduct() {
		ProductId productId = new ProductId("9000000112298");
		CategoryId categoryId = new CategoryId(1L);
		return createProduct(productId, categoryId);
	}

	public static Product createProduct(ProductId productId, CategoryId categoryId) {
		StoreId storeId = new StoreId("123456789");
		ProductInfo productInfo = createProductInfo();
		List<Image> images = createImages();
		return ProductFactory.create(productId, Set.of(categoryId), storeId, productInfo, images);
	}

	private static List<Image> createImages() {
		Image internalImage = new InternalImage("javaProgrammingBook.jpg");
		Image externalImage = new ExternalImage("https://example.com/javaProgrammingBook.jpg");
		return List.of(internalImage, externalImage);
	}

	private static ProductInfo createProductInfo() {
		String productName = "Java Book";
		Money price = new Money(1000);
		String detail = "Java Programming Book ";
		return new ProductInfo(productName, price, detail);
	}

	public static OrderSummary createOrderSummary(String number) {
		String productId = "9000000112298";
		return new OrderSummary(
			number,
			UUID.randomUUID().version(),
			"12345",
			"홍길동",
			10_000,
			"강감찬",
			OrderState.PAYMENT_WAITING.name(),
			LocalDateTime.now(),
			productId,
			"Java Book"
		);
	}

	public static List<MemberData> createFixedMemberDataList() {
		List<MemberData> result = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			String id = String.format("%05d", i);
			MemberData memberData = createMemberData(id, "james");
			result.add(memberData);
		}
		for (int i = 0; i < 20; i++) {
			String id = String.format("%05d", i + 20);
			MemberData memberData = createMemberData(id, "bob");
			result.add(memberData);
		}
		return result;
	}

	public static MemberData createMemberData(String id) {
		return createMemberData(id, "james");
	}

	public static MemberData createMemberData(String id, String name) {
		boolean blocked = false;
		return new MemberData(id, name, blocked);
	}

	public static Member createMember(String id) {
		MemberId memberId = new MemberId(id);
		String name = "홍길동";
		Address address = new Address(
			"서울 강남구 역삼동",
			"735-17",
			"06235"
		);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Password password = new Password(passwordEncoder.encode("12345"));
		Member member = new Member(
			memberId,
			name,
			address,
			password,
			MemberGrade.basic()
		);
		member.addEmail("hong1234@gmail.com");
		return member;
	}

	public static OrderLine createOrderLine(String productId, int price, int quantity) {
		return new OrderLine(new ProductId(productId), new Money(price), quantity);
	}

	public static Authentication createAdminAuthentication() {
		return new UsernamePasswordAuthenticationToken(
			"admin", "password",
			java.util.List.of(() -> "ROLE_ADMIN")
		);
	}
}
