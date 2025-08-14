package com.myshop.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myshop.order.domain.Money;
import com.myshop.order.domain.OrderLine;
import com.myshop.order.domain.Product;
import com.myshop.order.domain.ProductId;

class OrderLineTest {

	private Money price;
	private ProductId productId;

	@BeforeEach
	void setUp() {
		price = new Money(1000);
		productId = new ProductId("9000000112298");
	}

	@Test
	void canCreated(){
		int quantity = 2;

		OrderLine orderLine = new OrderLine(productId, price, quantity);

		assertNotNull(orderLine);
	}

	@Test
	void shouldCalculatedAmounts_whenOrderLineIsCreated(){
		int quantity = 2;

		OrderLine orderLine = new OrderLine(productId, price, quantity);

		assertEquals(2000, orderLine.getAmounts());
	}
}
