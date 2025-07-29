package com.nemo.effective_java.item73;

// high level exception
public class BankTransactionException extends Exception {
	public BankTransactionException(String message, Throwable cause) {
		super(message, cause);
	}
}
