package com.nemo.effective_java.item54.step02;

import java.util.List;

public class MarketClient {
	public static void main(String[] args) {
		Market market = new Market();
		List<Cheese> cheeses = market.getCheeses();
		if (cheeses.contains(Cheese.STILTON)) {
			System.out.println("STILTON is in stock.");
		}

		market.store(Cheese.STILTON);
		cheeses = market.getCheeses();
		if (cheeses.contains(Cheese.STILTON)) {
			System.out.println("STILTON is in stock.");
		}
	}
}
