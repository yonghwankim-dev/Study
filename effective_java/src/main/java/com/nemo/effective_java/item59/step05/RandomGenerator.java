package com.nemo.effective_java.item59.step05;

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class RandomGenerator {
	private static SplittableRandom rnd = new SplittableRandom();

	public static int random(int n) {
		return Math.abs(rnd.nextInt(n)) + 1;
	}

	public static void main(String[] args) {
		int n = 2 * (Integer.MAX_VALUE / 3);
		int low = (int)IntStream.range(0, 1_000_000)
			.parallel()
			.filter(i -> random(n) < n / 2)
			.count();
		System.out.println(low);
	}
}
