package com.myshop.order.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.command.domain.model.CancelPolicy;
import com.myshop.order.command.domain.model.Canceller;
import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.repository.OrderRepository;
import com.myshop.order.error.NoCancellablePermission;
import com.myshop.order.error.OrderNotFoundException;

@Service
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
