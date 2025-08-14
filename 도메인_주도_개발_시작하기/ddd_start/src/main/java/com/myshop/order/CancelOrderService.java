package com.myshop.order;

import org.springframework.transaction.annotation.Transactional;

public class CancelOrderService {

	private final OrderRepository orderRepository;

	public CancelOrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public void cancelOrder(OrderNumber number){
		Order order = orderRepository.findByNumber(number);
		if (order == null) {
			throw new NoOrderException(number);
		}
		order.cancel();
	}
}
