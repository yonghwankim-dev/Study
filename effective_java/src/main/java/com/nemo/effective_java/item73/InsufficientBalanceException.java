package com.nemo.effective_java.item73;

// low level exception
public class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException(String message) {
		super(message);
	}
}
