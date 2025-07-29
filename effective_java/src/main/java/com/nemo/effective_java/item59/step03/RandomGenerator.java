package com.nemo.effective_java.item59.step03;

import java.util.Random;

public class RandomGenerator {
	private static Random rnd = new Random();

	public static int random(int n) {
		return Math.abs(rnd.nextInt(n)) + 1;
	}

	public static void main(String[] args) {
		int n = 2 * (Integer.MAX_VALUE / 3);
		int low = 0;
		for (int i = 0; i < 1_000_000; i++) {
			if (random(n) < n / 2) {
				low++;
			}
		}
		System.out.println(low);
	}
}
