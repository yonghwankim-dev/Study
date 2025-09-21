package com.myshop.order.domain.model;

public interface CancelPolicy {
	boolean hasCancellationPermission(Order order, Canceller canceller);
}
