package com.nemo.effective_java.item02.step04;

public class PizzaClient {
	public static void main(String[] args) {
		NewYorkPizza pizza = new NewYorkPizza.Builder(NewYorkPizza.Size.SMALL)
			.addTopping(Pizza.Topping.SAUSAGE)
			.addTopping(Pizza.Topping.ONION)
			.build();
		System.out.println(pizza);

		Calzone calzone = new Calzone.Builder()
			.addTopping(Pizza.Topping.HAM)
			.sauceInside()
			.build();
		System.out.println(calzone);
	}
}
