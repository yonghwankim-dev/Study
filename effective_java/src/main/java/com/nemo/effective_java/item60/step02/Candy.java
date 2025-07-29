package com.nemo.effective_java.item60.step02;

public class Candy {
	public static void main(String[] args) {
		double funds = 1.00;
		int itemsBought = 0;
		for (double price = 0.10; funds >= price; price += 0.10) {
			funds -= price;
			itemsBought++;
		}
		System.out.println("buy the " + itemsBought + " count"); // expected: 4, actual: 3
		System.out.println("remain(dollar):" + funds); // expected: 0, actual: 0.3999999999999999
	}
}
