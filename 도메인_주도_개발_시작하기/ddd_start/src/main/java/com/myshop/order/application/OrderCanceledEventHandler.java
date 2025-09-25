package com.myshop.order.application;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.myshop.order.domain.model.OrderCanceledEvent;

@Service
public class OrderCanceledEventHandler {
	private final RefundService refundService;

	public OrderCanceledEventHandler(RefundService refundService) {
		this.refundService = refundService;
	}

	@EventListener(OrderCanceledEvent.class)
	public void handle(OrderCanceledEvent event) {
		refundService.refund(event.getOrderNumber());
	}
}
