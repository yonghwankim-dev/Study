package com.myshop.order.error;

public class VersionConflictException extends RuntimeException {

	public VersionConflictException() {
		super("Version conflict occurred");
	}
}
