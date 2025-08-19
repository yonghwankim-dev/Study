package com.myshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.Image;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductInfo;
import com.myshop.common.model.Money;
import com.myshop.member.domain.MemberId;
import com.myshop.order.domain.Address;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderLine;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderState;
import com.myshop.order.domain.Orderer;
import com.myshop.order.domain.Receiver;
import com.myshop.order.domain.ShippingInfo;
import com.myshop.store.domain.StoreId;

public class FixedDomainFactory {
	public static Order createOrder() {
		String orderId = "1234567890";
		return createOrder(orderId);
	}

	public static Order createOrder(String orderId) {
		OrderNo orderNo = new OrderNo(orderId);
		MemberId memberId = new MemberId("12345");
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
		StoreId storeId = new StoreId("123456789");
		Money price = new Money(1000);
		String detail = "Java Programming Book ";
		ProductInfo productInfo = new ProductInfo("Java Book", price, detail);
		List<Image> images = new ArrayList<>();
		return new Product(productId, Set.of(categoryId), storeId, productInfo, images);
	}
}
