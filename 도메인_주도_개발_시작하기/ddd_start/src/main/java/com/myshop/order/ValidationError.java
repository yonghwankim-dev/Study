package com.myshop.order;

public class ValidationError {
	private final String propertyName;
	private final String code;

	public ValidationError(String propertyName, String code) {
		this.propertyName = propertyName;
		this.code = code;
	}

	public static ValidationError of(String code) {
		return new ValidationError(null, code);
	}

	public static ValidationError of(String propertyName, String code) {
		return new ValidationError(propertyName, code);
	}
}
