package com.nemo.effective_java.item02.step04;

import java.util.Objects;

public class NewYorkPizza extends Pizza {
	public enum Size {SMALL, MEDIUM, LARGE}

	private final Size size;

	public static class Builder extends Pizza.Builder<Builder> {
		private final Size size;

		public Builder(Size size) {
			this.size = Objects.requireNonNull(size);
		}

		@Override
		public NewYorkPizza build() {
			return new NewYorkPizza(this);
		}

		@Override
		protected Builder self() {
			return this;
		}
	}

	private NewYorkPizza(Builder builder) {
		super(builder);
		size = builder.size;
	}

	@Override
	public String toString() {
		return String.format("NewYorkPizza(size=%s, toppings=%s)", size, toppings);
	}
}
