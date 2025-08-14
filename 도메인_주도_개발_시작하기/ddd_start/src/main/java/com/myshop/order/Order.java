package com.myshop.order;

public class Order {
	private OrderState state;
	private ShippingInfo shippingInfo;

	public Order(OrderState state, ShippingInfo shippingInfo) {
		this.state = state;
		this.shippingInfo = shippingInfo;
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
}
