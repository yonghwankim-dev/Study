package com.myshop.order;

public class Money {
	private final int value;

	public Money(int value) {
		this.value = value;
	}

	public Money add(Money money){
		return new Money(this.value + money.value);
	}

	public Money multiply(int multiplier) {
		return new Money(this.value * multiplier);
	}

	public int getValue() {
		return value;
	}
}
