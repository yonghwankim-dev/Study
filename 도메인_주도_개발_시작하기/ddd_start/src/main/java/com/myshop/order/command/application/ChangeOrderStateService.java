package com.myshop.order.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.model.OrderState;
import com.myshop.order.command.domain.repository.OrderRepository;

@Service
public class ChangeOrderStateService {

	private final OrderRepository orderRepository;

	public ChangeOrderStateService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public void changeOrderState(OrderNo orderNo, OrderState state) {
		Order order = orderRepository.findById(orderNo)
			.orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderNo));
		order.changeState(state);
	}
}
