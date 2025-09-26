package com.myshop.order.command.application;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.repository.OrderRepository;
import com.myshop.order.error.VersionConflictException;

@Service
public class StartShippingService {

	private final OrderRepository orderRepository;

	public StartShippingService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void startShipping(StartShippingRequest request) {
		Order order = orderRepository.findById(new OrderNo(request.getOrderNumber()))
			.orElseThrow(() -> new IllegalArgumentException("Order not found"));
		if (!order.matchVersion(request.getVersion())) {
			throw new VersionConflictException();
		}
		order.startShipping();
	}
}
