package com.myshop.order.command.domain.repository;

import java.util.List;
import java.util.Optional;

import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.model.Specification;

public interface OrderRepository {
	Optional<Order> findById(OrderNo id);

	Optional<Order> findByIdForUpdate(OrderNo id);

	Optional<Order> findAndForceVersionIncrement(OrderNo id);

	List<Order> findByOrdererId(String ordererId, int startRow, int size);

	List<Order> findAll();

	List<Order> findAll(Specification<Order> spec);

	void save(Order order);

	void delete(Order order);

	void deleteAll();

	OrderNo nextId();

}
