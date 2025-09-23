package com.myshop.lock.error;

public class NoLockException extends RuntimeException {
	public NoLockException() {
		super("No lock found for the given LockId.");
	}
}
