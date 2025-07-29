package com.nemo.effective_java.item53.step02;

public class MyCalculator {
	public static int min(int firstArg, int... args) {
		int min = firstArg;
		for (int arg : args) {
			if (arg < min) {
				min = arg;
			}
		}
		return min;
	}
}
