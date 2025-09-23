package com.myshop.lock.error;

public class LockingFailException extends RuntimeException {

	public LockingFailException() {
		super("Failed to acquire the lock.");
	}
}
