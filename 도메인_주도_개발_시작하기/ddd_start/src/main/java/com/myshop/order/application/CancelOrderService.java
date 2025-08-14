package com.myshop.order.application;

import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.NoOrderException;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNumber;
import com.myshop.order.domain.OrderRepository;

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
