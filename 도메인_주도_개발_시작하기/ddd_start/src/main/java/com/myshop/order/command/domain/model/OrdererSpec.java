package com.myshop.order.command.domain.model;

public class OrdererSpec implements Specification<Order> {
	private String ordererId;

	public OrdererSpec(String ordererId) {
		this.ordererId = ordererId;
	}

	@Override
	public boolean isSatisfiedBy(Order agg) {
		return agg.getOrderer().getMemberId().getId().equals(ordererId);
	}
}
