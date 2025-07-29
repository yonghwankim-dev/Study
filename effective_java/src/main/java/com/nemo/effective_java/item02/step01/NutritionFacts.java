package com.nemo.effective_java.item02.step01;

public class NutritionFacts {
	private int servingSize;    // (mL, 1회 제공량) required
	private int servings;        // (회, 총 n회 제공량) required
	private int calories;        // (1회 제공량당) optional
	private int fat;            // (g/1회 제공량) optional
	private int sodium;            // (mg/1회 제공량) optional
	private int carbohydrate;    // (g/1회 제공량) optional

	public NutritionFacts(int servingSize, int servings) {
		this(servingSize, servings, 0);
	}

	public NutritionFacts(int servingSize, int servings, int calories) {
		this(servingSize, servings, calories, 0);
	}

	public NutritionFacts(int servingSize, int servings, int calories, int fat) {
		this(servingSize, servings, calories, fat, 0);
	}

	public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
		this(servingSize, servings, calories, fat, sodium, 0);
	}

	public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
		this.servingSize = servingSize;
		this.servings = servings;
		this.calories = calories;
		this.fat = fat;
		this.sodium = sodium;
		this.carbohydrate = carbohydrate;
	}

	@Override
	public String toString() {
		return String.format(
			"NutritionFacts(servingSize=%d, servings=%d, calories=%d, fat=%d, sodium=%d, carbohydrate=%d)", servingSize,
			servings, calories, fat, sodium, carbohydrate);
	}
}
