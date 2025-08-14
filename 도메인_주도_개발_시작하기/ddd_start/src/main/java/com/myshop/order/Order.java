package com.myshop.order;

import java.util.List;
import java.util.Objects;

public class Order {
	private String orderNumber;
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
		verifyNotYetShipped();
		setShippingInfo(newShippingInfo);
	}

	private void verifyNotYetShipped() {
		if (state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING) {
			throw new IllegalStateException("already shipped");
		}
	}

	public void cancel(){
		verifyNotYetShipped();
		this.state = OrderState.CANCELED;
	}

	public void completePayment(){
		// todo: implement logic
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Order order = (Order)object;
		return Objects.equals(orderNumber, order.orderNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderNumber);
	}
}
