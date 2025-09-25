package com.myshop.order.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.domain.model.OrderNo;
import com.myshop.order.domain.repository.OrderRepository;

@Service
public class DeleteOrderService {

	private final OrderRepository orderRepository;

	public DeleteOrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public void deleteOrder(String orderId) {
		orderRepository.findById(new OrderNo(orderId))
			.ifPresent(orderRepository::delete);
	}
}
