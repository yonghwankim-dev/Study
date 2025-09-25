package com.myshop.eventstore.error;

public class PayloadConvertException extends RuntimeException {
	public PayloadConvertException(Exception e) {
		super("Failed to convert payload", e);
	}
}
