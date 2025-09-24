package com.myshop.message.query.dto;

public class AppendViewLogRequest {
	private String memberId;
	private String productId;

	public AppendViewLogRequest() {
	}

	public AppendViewLogRequest(String memberId, String productId) {
		this.memberId = memberId;
		this.productId = productId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
