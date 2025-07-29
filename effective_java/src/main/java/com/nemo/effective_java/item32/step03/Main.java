package com.nemo.effective_java.item32.step03;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	public static void main(String[] args) {
		String[] attributes = pickTwo("좋은", "빠른", "저렴한"); // RuntimeError : ClassCastException, Object[] -> String[] (x)
		System.out.println(Arrays.toString(attributes));
	}

	private static <T> T[] pickTwo(T a, T b, T c) {
		int i = ThreadLocalRandom.current().nextInt(3);
		return switch (i) {
			case 0 -> toArray(a, b);
			case 1 -> toArray(a, c);
			case 2 -> toArray(b, c);
			default -> null; // unreachable
		};
	}

	private static <T> T[] toArray(T... args) {
		return args;
	}
}
