package com.myshop.catalog.query.product.dto;

public class ViewLog implements java.io.Serializable {
	private String memberId;
	private String productId;
	private String viewedAt;

	public ViewLog() {
	}

	public ViewLog(String memberId, String productId, java.time.LocalDateTime viewedAt) {
		this.memberId = memberId;
		this.productId = productId;
		this.viewedAt = viewedAt.toString();
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

	public String getViewedAt() {
		return viewedAt;
	}

	public void setViewedAt(String viewedAt) {
		this.viewedAt = viewedAt;
	}

	@Override
	public String toString() {
		return "ViewLog{" +
			"memberId='" + memberId + '\'' +
			", productId='" + productId + '\'' +
			", viewedAt='" + viewedAt + '\'' +
			'}';
	}
}
