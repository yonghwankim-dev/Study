package com.nemo.effective_java.item54.step04;

import java.util.ArrayList;
import java.util.List;

public class Market {

	private static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];

	private final List<Cheese> cheesesInStock = new ArrayList<>();

	public void store(Cheese cheese) {
		cheesesInStock.add(cheese);
	}

	public Cheese[] getCheeses() {
		// return cheesesInStock.toArray(new Cheese[cheesesInStock.size()]); // bad idea
		return cheesesInStock.toArray(EMPTY_CHEESE_ARRAY);
	}
}
