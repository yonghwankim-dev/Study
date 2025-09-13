package com.myshop.order;

import java.util.List;

public class ValidationErrorException extends RuntimeException {

	private final List<ValidationError> errors;

	public ValidationErrorException(List<ValidationError> errors) {
		super("Validation errors occurred");
		this.errors = errors;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}
}
