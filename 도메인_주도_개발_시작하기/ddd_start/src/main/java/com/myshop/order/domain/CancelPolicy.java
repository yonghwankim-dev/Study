package com.myshop.order.domain;

public interface CancelPolicy {
	boolean hasCancellationPermission(Order order, Canceller canceller);
}
