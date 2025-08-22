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
		return Optional.ofNullable(orderStore.get(id));
	}

	@Override
	public List<Order> findByOrdererId(String ordererId, int startRow, int size) {
		return orderStore.values().stream()
			.filter(order -> order.getOrderer().getMemberId().getId().equals(ordererId))
			.sorted((order1, order2) -> order2.getOrderNo().compareTo(order1.getOrderNo()))
			.skip(startRow)
			.limit(size)
			.toList();
	}

	@Override
	public void save(Order order) {
		if (order == null) {
			throw new IllegalArgumentException("Order cannot be null");
		}
		orderStore.put(order.getOrderNo(), order);
	}

	@Override
	public void delete(Order order) {
		if (order == null) {
			throw new IllegalArgumentException("Order cannot be null");
		}
		orderStore.remove(order.getOrderNo());
	}

	@Override
	public void deleteAll() {

	}
}
