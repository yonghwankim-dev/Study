package com.myshop.order.query.dto;

import java.util.List;

import com.myshop.order.application.OrderProduct;
import com.myshop.order.domain.Orderer;
import com.myshop.order.domain.ShippingInfo;

public class OrderRequest {
	private Orderer orderer;
	private ShippingInfo shippingInfo;
	private List<OrderProduct> orderProducts;

	public OrderRequest(Orderer orderer, ShippingInfo shippingInfo, List<OrderProduct> orderProducts) {
		this.orderer = orderer;
		this.shippingInfo = shippingInfo;
		this.orderProducts = orderProducts;
	}

	public Orderer getOrderer() {
		return orderer;
	}

	public ShippingInfo getShippingInfo() {
		return shippingInfo;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}
}
