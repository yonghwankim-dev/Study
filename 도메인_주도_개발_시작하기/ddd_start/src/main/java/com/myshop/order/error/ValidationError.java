package com.myshop.order.error;

import java.util.Objects;

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

	public boolean hasName() {
		return propertyName != null && !propertyName.isEmpty();
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getCode() {
		return code;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		ValidationError that = (ValidationError)object;
		return Objects.equals(propertyName, that.propertyName) && Objects.equals(code, that.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(propertyName, code);
	}

	@Override
	public String toString() {
		return "ValidationError{" +
			"propertyName='" + propertyName + '\'' +
			", code='" + code + '\'' +
			'}';
	}
}
