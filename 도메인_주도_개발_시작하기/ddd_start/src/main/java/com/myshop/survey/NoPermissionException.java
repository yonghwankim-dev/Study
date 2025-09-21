package com.myshop.survey;

public class NoPermissionException extends RuntimeException {
	public NoPermissionException() {
		super("No permission to create survey");
	}
}
