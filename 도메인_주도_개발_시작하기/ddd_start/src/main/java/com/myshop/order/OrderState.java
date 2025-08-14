package com.myshop.order;

public enum OrderState {
	PAYMENT_WAITING{
		@Override
		public boolean isShippingChangeable() {
			return true;
		}
	},
	PREPARING{
		@Override
		public boolean isShippingChangeable() {
			return true;
		}
	},
	SHIPPED,
	DELIVERING,
	DELIVERY_COMPLETED;

	public boolean isShippingChangeable() {
		return false;
	}
}
