package com.myshop.order.application;

import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.OrderNotFoundException;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;

public class CancelOrderService {

	private final OrderRepository orderRepository;

	public CancelOrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public void cancelOrder(OrderNo orderNo) {
		Order order = orderRepository.findById(orderNo)
			.orElseThrow(() -> new OrderNotFoundException(orderNo));
		order.cancel();
	}
}
