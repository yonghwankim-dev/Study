package com.myshop.member;

public class EmptyPropertyException extends RuntimeException {
	private final String propertyName;

	public EmptyPropertyException(String propertyName) {
		super(propertyName + " is empty");
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}
}
