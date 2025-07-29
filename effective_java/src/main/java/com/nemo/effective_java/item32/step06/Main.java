package com.nemo.effective_java.item32.step06;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	public static void main(String[] args) {
		List<String> attributes = pickTwo("좋은", "빠른", "저렴한");
		System.out.println(attributes);
	}

	private static <T> List<T> pickTwo(T a, T b, T c) {
		int i = ThreadLocalRandom.current().nextInt(3);
		return switch (i) {
			case 0 -> List.of(a, b);
			case 1 -> List.of(a, c);
			case 2 -> List.of(b, c);
			default -> null; // unreachable
		};
	}
}
