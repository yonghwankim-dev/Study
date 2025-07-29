package com.nemo.effective_java.item06.step03;

public class LongSum {
	public static long sumUsingBox(long n) {
		Long sum = 0L;
		for (long i = 0; i < n; i++) {
			sum += i;
		}
		return sum;
	}

	public static long sumUsingPrimitive(long n) {
		long sum = 0L;
		for (long i = 0; i < n; i++) {
			sum += i;
		}
		return sum;
	}

	public static void main(String[] args) {
		long n = Integer.MAX_VALUE;
		long start = System.currentTimeMillis();
		sumUsingBox(n);
		long end = System.currentTimeMillis() - start;
		System.out.println("Elapsed time using Box: " + end + " ms"); // 3171ms

		start = System.currentTimeMillis();
		sumUsingPrimitive(n);
		end = System.currentTimeMillis() - start;
		System.out.println("Elapsed time using Primitive: " + end + " ms"); // 711ms
	}
}
