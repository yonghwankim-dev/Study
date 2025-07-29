package com.nemo.effective_java.item60.step04;

public class Candy {
	public static void main(String[] args) {
		int funds = 100;
		int itemsBought = 0;
		for (int price = 10; funds >= price; price += 10) {
			funds -= price;
			itemsBought++;
		}
		System.out.println("buy the " + itemsBought + " count"); // expected: 4, actual: 4
		System.out.println("remain(dollar):" + funds); // expected: 0, actual: 0
	}
}
