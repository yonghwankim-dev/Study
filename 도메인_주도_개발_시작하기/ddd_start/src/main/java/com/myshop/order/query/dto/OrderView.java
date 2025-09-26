package com.myshop.order.query.dto;

import com.myshop.catalog.domain.product.Product;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.model.OrderState;

public class OrderView {

	private final String number;
	private final OrderState state;
	private final String memberName;
	private final String memberId;
	private final String productName;

	public OrderView(OrderNo number, OrderState state, String memberName, MemberId memberId, String productName) {
		this.number = number.getId();
		this.state = state;
		this.memberName = memberName;
		this.memberId = memberId.getId();
		this.productName = productName;
	}

	public OrderView(Order order, Member member, Product product) {
		this.number = order.getOrderNo().getId();
		this.state = order.getState();
		this.memberName = member.getName();
		this.memberId = member.getId().getId();
		this.productName = product.getProductInfo().getProductName();
	}

	public String getNumber() {
		return number;
	}

	public OrderState getState() {
		return state;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getProductName() {
		return productName;
	}
}
