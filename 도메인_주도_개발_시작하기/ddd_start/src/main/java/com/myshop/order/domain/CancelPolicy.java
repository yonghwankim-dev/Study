package com.myshop.order.domain;

public class CancelPolicy {
	public boolean hasCancellationPermission(Order order, Canceller canceller) {
		return true;
	}
}
