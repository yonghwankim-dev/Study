package com.myshop.order;

import java.util.List;

public class Order {
	private OrderState state;
	private ShippingInfo shippingInfo;
	private List<OrderLine> orderLines;
	private Money totalAmounts;

	public Order(OrderState state, ShippingInfo shippingInfo, List<OrderLine> orderLines) {
		this.state = state;
		setOrderLines(orderLines);
		setShippingInfo(shippingInfo);
	}

	private void setOrderLines(List<OrderLine> orderLines) {
		verifyAtLeastOneOrMoreOrderLines(orderLines);
		this.orderLines = orderLines;
		calculateTotalAmount();
	}

	private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
		if (orderLines == null || orderLines.isEmpty()) {
			throw new IllegalArgumentException("no OrderLine");
		}
	}

	private void calculateTotalAmount() {
		int sum = orderLines.stream()
			.mapToInt(OrderLine::getAmounts)
			.sum();
		this.totalAmounts = new Money(sum);
	}

	private void setShippingInfo(ShippingInfo shippingInfo) {
		if (shippingInfo == null) {
			throw new IllegalArgumentException("no ShippingInfo");
		}
		this.shippingInfo = shippingInfo;
	}

	public void changeShipped(){
		// todo: implement logic
	}

	public void changeShippingInfo(ShippingInfo newShippingInfo) {
		if (!isShippingChangeable()) {
			throw new IllegalStateException("can't change shipping in " + state);
		}
		this.shippingInfo = newShippingInfo;
	}

	private boolean isShippingChangeable() {
		return state == OrderState.PAYMENT_WAITING ||
			state == OrderState.PREPARING;
	}

	public void cancel(){
		// todo: implement logic
	}

	public void completePayment(){
		// todo: implement logic
	}
}
