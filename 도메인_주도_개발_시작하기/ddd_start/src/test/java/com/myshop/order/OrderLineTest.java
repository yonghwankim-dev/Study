package com.myshop.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderLineTest {

	@Test
	void canCreated(){
		Product product = new Product();
		int price = 1000;
		int quantity = 2;
		OrderLine orderLine = new OrderLine(product, price, quantity);
		assertNotNull(orderLine);
	}

}
