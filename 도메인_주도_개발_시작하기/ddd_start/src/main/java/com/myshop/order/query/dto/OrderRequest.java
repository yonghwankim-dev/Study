package com.myshop.order.query.dto;

import java.util.List;

import com.myshop.member.domain.MemberId;
import com.myshop.order.application.OrderProduct;
import com.myshop.order.domain.ShippingInfo;

public class OrderRequest {
	private List<OrderProduct> orderProducts;
	private MemberId ordererMemberId;
	private ShippingInfo shippingInfo;

	public OrderRequest() {
	}

	public OrderRequest(List<OrderProduct> orderProducts, MemberId ordererMemberId, ShippingInfo shippingInfo) {
		this.orderProducts = orderProducts;
		this.ordererMemberId = ordererMemberId;
		this.shippingInfo = shippingInfo;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public MemberId getOrdererMemberId() {
		return ordererMemberId;
	}

	public ShippingInfo getShippingInfo() {
		return shippingInfo;
	}

	public void setOrdererMemberId(MemberId ordererMemberId) {
		this.ordererMemberId = ordererMemberId;
	}
}
