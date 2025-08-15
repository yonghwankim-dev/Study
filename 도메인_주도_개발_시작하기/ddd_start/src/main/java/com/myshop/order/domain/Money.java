package com.myshop.order.domain;

import java.util.Objects;

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

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Money money = (Money)object;
		return value == money.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
