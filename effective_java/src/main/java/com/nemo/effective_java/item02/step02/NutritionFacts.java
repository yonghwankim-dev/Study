package com.nemo.effective_java.item02.step02;

public class NutritionFacts {
	private int servingSize;    // (mL, 1회 제공량) required
	private int servings;        // (회, 총 n회 제공량) required
	private int calories;        // (1회 제공량당) optional
	private int fat;            // (g/1회 제공량) optional
	private int sodium;            // (mg/1회 제공량) optional
	private int carbohydrate;    // (g/1회 제공량) optional

	public NutritionFacts() {

	}

	public void setServingSize(int servingSize) {
		this.servingSize = servingSize;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public void setFat(int fat) {
		this.fat = fat;
	}

	public void setSodium(int sodium) {
		this.sodium = sodium;
	}

	public void setCarbohydrate(int carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	@Override
	public String toString() {
		return String.format(
			"NutritionFacts(servingSize=%d, servings=%d, calories=%d, fat=%d, sodium=%d, carbohydrate=%d)", servingSize,
			servings, calories, fat, sodium, carbohydrate);
	}
}
