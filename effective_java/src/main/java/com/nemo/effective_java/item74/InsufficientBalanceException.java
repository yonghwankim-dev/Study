package com.nemo.effective_java.item74;

// low level exception
public class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException(String message) {
		super(message);
	}
}
