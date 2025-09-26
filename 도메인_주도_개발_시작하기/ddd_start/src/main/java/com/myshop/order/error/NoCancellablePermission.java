package com.myshop.order.error;

public class NoCancellablePermission extends RuntimeException {
	public NoCancellablePermission() {
		super("No permission to cancel order");
	}
}
