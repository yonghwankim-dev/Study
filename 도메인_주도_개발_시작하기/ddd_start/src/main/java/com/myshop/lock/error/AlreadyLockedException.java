package com.myshop.lock.error;

public class AlreadyLockedException extends RuntimeException {

	public AlreadyLockedException() {
		super("The resource is already locked.");
	}
}
