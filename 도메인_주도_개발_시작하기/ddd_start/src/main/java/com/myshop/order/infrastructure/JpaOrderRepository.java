package com.myshop.order.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;
import com.myshop.order.domain.Specification;

@Repository
public class JpaOrderRepository implements OrderRepository {

	private final SpringDataJpaOrderRepository repository;

	public JpaOrderRepository(SpringDataJpaOrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<Order> findById(OrderNo id) {
		return repository.findById(id);
	}

	@Override
	public List<Order> findByOrdererId(String ordererId, int startRow, int size) {
		return repository.findByOrdererId(ordererId, startRow, size);
	}

	@Override
	public List<Order> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Order> findAll(Specification<Order> spec) {
		List<Order> allOrders = findAll();
		return allOrders.stream()
			.filter(spec::isSatisfiedBy)
			.toList();
	}

	@Override
	public void save(Order order) {
		if (order == null) {
			throw new IllegalArgumentException("Order cannot be null");
		}
		repository.save(order);
	}

	@Override
	public void delete(Order order) {
		if (order == null) {
			throw new IllegalArgumentException("Order cannot be null");
		}
		repository.delete(order);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
}
