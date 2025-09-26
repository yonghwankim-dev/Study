package com.myshop.order.command.domain.model;

public interface CancelPolicy {
	boolean hasCancellationPermission(Order order, Canceller canceller);
}
