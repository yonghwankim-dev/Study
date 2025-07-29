package com.nemo.effective_java.item54.step01;

import java.util.ArrayList;
import java.util.List;

public class Market {

	private final List<Cheese> cheesesInStock = new ArrayList<>();

	public void store(Cheese cheese) {
		cheesesInStock.add(cheese);
	}

	public List<Cheese> getCheeses() {
		return cheesesInStock.isEmpty() ? null : new ArrayList<>(cheesesInStock);
	}
}
