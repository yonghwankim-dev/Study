package com.myshop.member.query.dto;

public class MemberSearchRequest {

	private boolean onlyNotBlocked;
	private String name;

	public MemberSearchRequest(boolean onlyNotBlocked, String name) {
		this.onlyNotBlocked = onlyNotBlocked;
		this.name = name;
	}

	public boolean isOnlyNotBlocked() {
		return onlyNotBlocked;
	}

	public String getName() {
		return name;
	}
}
