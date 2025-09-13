package com.myshop.member;

public class EmptyPropertyException extends RuntimeException {
	public EmptyPropertyException(String propertyName) {
		super(propertyName + " is empty");
	}
}
