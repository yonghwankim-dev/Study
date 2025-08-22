package com.myshop.order.domain;

public class OrdererSpec implements Specification<Order> {
	private final String ordererId;

	public OrdererSpec(String ordererId) {
		this.ordererId = ordererId;
	}

	@Override
	public boolean isSatisfiedBy(Order agg) {
		return agg.getOrderer().getMemberId().getId().equals(ordererId);
	}
}
