package com.myshop.order.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.repository.OrderRepository;

@Service
public class SearchOrderService {
	private final OrderRepository orderRepository;

	public SearchOrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public Order search(OrderNo orderNo) {
		return this.orderRepository.findByIdForUpdate(orderNo).orElseThrow();
	}
}
