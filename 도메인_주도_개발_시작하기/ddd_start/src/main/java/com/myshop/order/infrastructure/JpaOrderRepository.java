package com.myshop.order.infrastructure;

import org.springframework.stereotype.Repository;

import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;

@Repository
public class JpaOrderRepository implements OrderRepository {

	private final SpringDataJpaOrderRepository repository;

	public JpaOrderRepository(SpringDataJpaOrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public Order findById(OrderNo id) {
		return repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
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
}
