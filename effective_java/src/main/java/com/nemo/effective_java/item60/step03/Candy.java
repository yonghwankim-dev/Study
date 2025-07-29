package com.nemo.effective_java.item60.step03;

import java.math.BigDecimal;

public class Candy {
	private static final BigDecimal TEM_CENTS = new BigDecimal("0.10");

	public static void main(String[] args) {
		int itemsBought = 0;
		BigDecimal funds = new BigDecimal("1.00");
		for (BigDecimal price = TEM_CENTS; funds.compareTo(price) >= 0; price = price.add(TEM_CENTS)) {
			funds = funds.subtract(price);
			itemsBought++;
		}
		System.out.println("buy the " + itemsBought + " count"); // expected: 4, actual: 4
		System.out.println("remain(dollar):" + funds); // expected: 0.00, actual: 0.00
	}
}
