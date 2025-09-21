package com.myshop.order.domain.model;

public class Canceller {
	private String memberId;

	public Canceller(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}
}
