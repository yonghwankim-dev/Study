package com.myshop.order.application;

import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.OrderNotFoundException;
import com.myshop.order.domain.CancelPolicy;
import com.myshop.order.domain.Canceller;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.repository.OrderRepository;

public class CancelOrderService {

	private final OrderRepository orderRepository;
	private final CancelPolicy cancelPolicy;

	public CancelOrderService(OrderRepository orderRepository, CancelPolicy cancelPolicy) {
		this.orderRepository = orderRepository;
		this.cancelPolicy = cancelPolicy;
	}

	@Transactional
	public void cancelOrder(OrderNo orderNo, Canceller canceller) {
		Order order = orderRepository.findById(orderNo)
			.orElseThrow(() -> new OrderNotFoundException(orderNo));
		if (!cancelPolicy.hasCancellationPermission(order, canceller)) {
			throw new NoCancellablePermission();
		}
		order.cancel();
	}
}
