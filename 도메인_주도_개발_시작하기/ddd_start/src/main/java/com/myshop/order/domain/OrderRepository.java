package com.myshop.order.domain;

public interface OrderRepository {
	Order findById(OrderNo id);
	void save(Order order);
	void delete(Order order);
}
