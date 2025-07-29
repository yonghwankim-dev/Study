package com.nemo.effective_java.item80.step09;

import java.util.Arrays;

public class ForkJoinSumClient {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		long[] numbers = new long[100_000_000];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = i + 1;
		}

		long result = Arrays.stream(numbers)
			.parallel()
			.sum();
		System.out.println("Sum of Array: " + result);

		long end = System.currentTimeMillis();
		System.out.println("Time : " + (end - start) + "ms");
	}
}
