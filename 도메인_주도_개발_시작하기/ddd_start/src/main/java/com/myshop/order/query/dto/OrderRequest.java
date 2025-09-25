package com.myshop.order.query.dto;

import java.util.List;

import com.myshop.order.application.OrderProduct;
import com.myshop.order.domain.model.ShippingInfo;

public class OrderRequest {
	private List<OrderProduct> orderProducts;
	private String ordererMemberId;
	private ShippingInfo shippingInfo;

	public OrderRequest() {
	}

	public OrderRequest(List<OrderProduct> orderProducts, String ordererMemberId, ShippingInfo shippingInfo) {
		this.orderProducts = orderProducts;
		this.ordererMemberId = ordererMemberId;
		this.shippingInfo = shippingInfo;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public String getOrdererMemberId() {
		return ordererMemberId;
	}

	public ShippingInfo getShippingInfo() {
		return shippingInfo;
	}

	public void setOrdererMemberId(String ordererMemberId) {
		this.ordererMemberId = ordererMemberId;
	}
}
