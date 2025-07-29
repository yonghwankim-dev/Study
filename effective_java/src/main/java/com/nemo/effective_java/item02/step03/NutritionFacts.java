package com.nemo.effective_java.item02.step03;

public class NutritionFacts {
	private int servingSize;    // (mL, 1회 제공량) required
	private int servings;        // (회, 총 n회 제공량) required
	private int calories;        // (1회 제공량당) optional
	private int fat;            // (g/1회 제공량) optional
	private int sodium;            // (mg/1회 제공량) optional
	private int carbohydrate;    // (g/1회 제공량) optional

	public static class Builder {
		// Required parameters
		private final int servingSize;
		private final int servings;

		// Optional parameters - initialized to default values
		private int calories = 0;
		private int fat = 0;
		private int sodium = 0;
		private int carbohydrate = 0;

		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}

		public Builder calories(int val) {
			calories = val;
			return this;
		}

		public Builder fat(int val) {
			fat = val;
			return this;
		}

		public Builder sodium(int val) {
			sodium = val;
			return this;
		}

		public Builder carbohydrate(int val) {
			carbohydrate = val;
			return this;
		}

		public NutritionFacts build() {
			return new NutritionFacts(this);
		}
	}

	private NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}

	@Override
	public String toString() {
		return String.format(
			"NutritionFacts(servingSize=%d, servings=%d, calories=%d, fat=%d, sodium=%d, carbohydrate=%d)", servingSize,
			servings, calories, fat, sodium, carbohydrate);
	}
}
