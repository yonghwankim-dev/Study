package com.nemo.effective_java.item02.step02;

public class NutritionFactsClient {
	public static void main(String[] args) {
		NutritionFacts cocaCola = new NutritionFacts();
		cocaCola.setServingSize(120);
		cocaCola.setServingSize(8);
		cocaCola.setCalories(100);
		cocaCola.setSodium(35);
		cocaCola.setCarbohydrate(27);
		System.out.println(cocaCola);
	}
}
