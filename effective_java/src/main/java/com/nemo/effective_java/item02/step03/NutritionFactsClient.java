package com.nemo.effective_java.item02.step03;

public class NutritionFactsClient {
	public static void main(String[] args) {
		NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
			.calories(100)
			.sodium(35)
			.carbohydrate(27)
			.build();
		System.out.println(cocaCola);
	}
}
