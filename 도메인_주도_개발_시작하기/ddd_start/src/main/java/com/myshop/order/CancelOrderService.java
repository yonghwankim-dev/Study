package com.myshop.order;

import org.springframework.transaction.annotation.Transactional;

public class CancelOrderService {

	private final OrderRepository orderRepository;

	public CancelOrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public void cancelOrder(String orderId){
		Order order = findOrderById(orderId);
		if (order == null) {
			throw new OrderNotFoundException(orderId);
		}
		order.cancel();
	}

	private Order findOrderById(String orderId) {
		return orderRepository.findByNumber(new OrderNumber(orderId));
	}
}
