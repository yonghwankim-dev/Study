package com.myshop.order.domain;

import java.util.List;
import java.util.Optional;

import com.myshop.order.query.dto.OrderSummary;

public interface OrderRepository {
	Optional<Order> findById(OrderNo id);

	List<Order> findByOrdererId(String ordererId, int startRow, int size);

	List<Order> findAll();

	List<Order> findAll(Specification<Order> spec);

	List<Order> findAll(org.springframework.data.jpa.domain.Specification<OrderSummary> spec);

	void save(Order order);

	void delete(Order order);

	void deleteAll();
}
