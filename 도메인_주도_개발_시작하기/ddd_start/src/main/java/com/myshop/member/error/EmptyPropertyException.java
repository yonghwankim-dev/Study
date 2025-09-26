package com.myshop.member.error;

public class EmptyPropertyException extends RuntimeException {
	private final String propertyName;
	private final String code;

	public EmptyPropertyException(String propertyName) {
		super(propertyName + " is empty");
		this.propertyName = propertyName;
		this.code = "empty";
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getCode() {
		return code;
	}
}
