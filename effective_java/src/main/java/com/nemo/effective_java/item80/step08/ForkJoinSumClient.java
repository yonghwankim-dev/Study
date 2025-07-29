package com.nemo.effective_java.item80.step08;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinSumClient {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		long[] numbers = new long[100_000_000];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = i + 1;
		}

		ForkJoinPool forkJoinPool = new ForkJoinPool();
		SumTask task = new SumTask(numbers, 0, numbers.length);
		Long result = forkJoinPool.invoke(task);

		System.out.println("Sum of Array: " + result);

		forkJoinPool.shutdown();
		long end = System.currentTimeMillis();
		System.out.println("Time : " + (end - start) + "ms");
	}
}
