package com.myshop.member;

public class DuplicateIdException extends RuntimeException {
	public DuplicateIdException(String id) {
		super("Duplicate id: " + id);
	}
}
