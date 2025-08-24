package com.myshop.member;

public class NoMemberException extends RuntimeException {
	public NoMemberException(String memberId) {
		super("No member found with ID: " + memberId);
	}
}
