package com.nemo.effective_java.item02.step04;

import java.util.HashSet;
import java.util.Set;

public abstract class Pizza {
	public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}

	final Set<Topping> toppings;

	abstract static class Builder<T extends Builder<T>> {
		Set<Topping> toppings = new HashSet<>();

		public T addTopping(Topping topping) {
			toppings.add(topping);
			return self();
		}

		abstract Pizza build();

		protected abstract T self();
	}

	Pizza(Builder<?> builder) {
		toppings = Set.copyOf(builder.toppings);
	}
}
