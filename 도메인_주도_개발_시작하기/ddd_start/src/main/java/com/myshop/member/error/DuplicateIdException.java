package com.myshop.member.error;

public class DuplicateIdException extends RuntimeException {

	private final String id;

	public DuplicateIdException(String id) {
		super("Duplicate id: " + id);
		this.id = id;
	}

	public String getPropertyName() {
		return "id";
	}

	public String getId() {
		return id;
	}
}
