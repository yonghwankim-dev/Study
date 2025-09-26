package com.myshop.order.command.infrastructure;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.myshop.order.command.application.RefundService;
import com.myshop.order.command.domain.model.OrderCanceledEvent;

@Service
public class OrderCanceledEventHandler {
	private final RefundService refundService;

	public OrderCanceledEventHandler(RefundService refundService) {
		this.refundService = refundService;
	}

	@Async
	@TransactionalEventListener(classes = OrderCanceledEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handle(OrderCanceledEvent event) {
		refundService.refund(event.getOrderNumber());
	}
}
