package com.myshop.order;

public interface OrderRepository {

	Order save(Order order);
	Order findById(String orderId);
}
