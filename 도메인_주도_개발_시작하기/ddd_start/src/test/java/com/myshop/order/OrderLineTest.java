package com.myshop.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myshop.order.domain.Money;
import com.myshop.order.domain.OrderLine;
import com.myshop.order.domain.Product;

class OrderLineTest {

	private Money price;

	@BeforeEach
	void setUp() {
		price = new Money(1000);
	}

	@Test
	void canCreated(){
		Product product = new Product();
		int quantity = 2;

		OrderLine orderLine = new OrderLine(product, price, quantity);

		assertNotNull(orderLine);
	}

	@Test
	void shouldCalculatedAmounts_whenOrderLineIsCreated(){
		Product product = new Product();
		int quantity = 2;

		OrderLine orderLine = new OrderLine(product, price, quantity);

		assertEquals(2000, orderLine.getAmounts());
	}
}
