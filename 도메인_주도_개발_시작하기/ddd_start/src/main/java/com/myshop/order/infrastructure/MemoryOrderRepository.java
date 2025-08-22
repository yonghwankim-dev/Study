package com.myshop.order.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;

public class MemoryOrderRepository implements OrderRepository {

	private Map<OrderNo, Order> orderStore = new HashMap<>();
	
	@Override
	public Optional<Order> findById(OrderNo id) {
		return Optional.empty();
	}

	@Override
	public List<Order> findByOrdererId(String ordererId, int startRow, int size) {
		return null;
	}

	@Override
	public void save(Order order) {

	}

	@Override
	public void delete(Order order) {

	}

	@Override
	public void deleteAll() {

	}
}
