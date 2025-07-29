package com.nemo.effective_java.item54.step04;

import java.util.Arrays;

public class MarketClient {
	public static void main(String[] args) {
		Market market = new Market();
		Cheese[] cheeses = market.getCheeses();
		if (contains(cheeses, Cheese.STILTON)) {
			System.out.println("STILTON is in stock.");
		}

		market.store(Cheese.STILTON);
		market.store(Cheese.CHEDDAR);
		cheeses = market.getCheeses();
		if (contains(cheeses, Cheese.STILTON)) {
			System.out.println("STILTON is in stock.");
		}
	}

	private static boolean contains(Cheese[] cheeses, Cheese target) {
		return Arrays.stream(cheeses)
			.anyMatch(c -> c == target);
	}
}
