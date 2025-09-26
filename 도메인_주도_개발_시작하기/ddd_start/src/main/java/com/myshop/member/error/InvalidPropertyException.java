package com.myshop.member.error;

public class InvalidPropertyException extends RuntimeException {

	private final String propertyName;
	private final String code;

	public InvalidPropertyException(String propertyName) {
		super(propertyName + " has an invalid format");
		this.propertyName = propertyName;
		this.code = "invalid";
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getCode() {
		return code;
	}
}
