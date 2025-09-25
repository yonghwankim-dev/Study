package com.myshop.order.query.dto;

import java.util.List;

import com.myshop.order.application.OrderProduct;

public class OrderRequest {
	private List<OrderProduct> orderProducts;
	private String ordererMemberId;
	private String receiverName;
	private String receiverPhone;
	private String message;
	private String address1;
	private String address2;
	private String zipCode;

	public OrderRequest() {
	}

	public OrderRequest(
		List<OrderProduct> orderProducts,
		String ordererMemberId,
		String receiverName,
		String receiverPhone,
		String message,
		String address1,
		String address2,
		String zipCode) {
		this.orderProducts = orderProducts;
		this.ordererMemberId = ordererMemberId;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.message = message;
		this.address1 = address1;
		this.address2 = address2;
		this.zipCode = zipCode;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public String getOrdererMemberId() {
		return ordererMemberId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public String getMessage() {
		return message;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public void setOrdererMemberId(String ordererMemberId) {
		this.ordererMemberId = ordererMemberId;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
