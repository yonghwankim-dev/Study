package com.myshop.order.domain;

import java.util.Optional;

public interface OrderRepository {
	Optional<Order> findById(OrderNo id);

	void save(Order order);

	void delete(Order order);

	void deleteAll();
}
