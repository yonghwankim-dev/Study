package com.nemo.effective_java.item53.step01;

public class MyCalculator {
	public static int sum(int... args) {
		int sum = 0;
		for (int i : args) {
			sum += i;
		}
		return sum;
	}

	public static int min(int... args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("At least one argument is required.");
		}
		int min = args[0];
		for (int i = 1; i < args.length; i++) {
			if (args[i] < min) {
				min = args[i];
			}
		}
		return min;
	}
}
