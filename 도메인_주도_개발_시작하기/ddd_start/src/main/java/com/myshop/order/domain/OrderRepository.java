package com.myshop.order.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
	Optional<Order> findById(OrderNo id);

	List<Order> findByOrdererId(String ordererId, int startRow, int size);

	List<Order> findAll();

	List<Order> findAll(Specification<Order> spec);

	void save(Order order);

	void delete(Order order);

	void deleteAll();

	OrderNo nextId();
}
