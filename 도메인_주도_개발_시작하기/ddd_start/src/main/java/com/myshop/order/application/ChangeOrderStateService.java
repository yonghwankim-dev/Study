package com.myshop.order.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.domain.model.Order;
import com.myshop.order.domain.model.OrderState;
import com.myshop.order.domain.repository.OrderRepository;

@Service
public class ChangeOrderStateService {

	private final OrderRepository orderRepository;

	public ChangeOrderStateService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public void changeOrderState(Order order, OrderState state) {
		order.changeState(state);
	}
}
