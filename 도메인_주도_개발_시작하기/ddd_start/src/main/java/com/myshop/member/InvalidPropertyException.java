package com.myshop.member;

public class InvalidPropertyException extends RuntimeException {

	private final String propertyName;
	private final String reason;

	public InvalidPropertyException(String propertyName, String reason) {
		super(propertyName + " is invalid: " + reason);
		this.propertyName = propertyName;
		this.reason = reason;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getReason() {
		return reason;
	}
}
