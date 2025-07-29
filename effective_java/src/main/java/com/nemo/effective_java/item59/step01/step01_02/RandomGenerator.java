package com.nemo.effective_java.item59.step01.step01_02;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RandomGenerator {
	private static Random rnd = new Random();

	public static int random(int n) {
		return Math.abs(rnd.nextInt()) % n;
	}

	public static void main(String[] args) {
		int n = 1_000_000;  // n을 2의 제곱수로 설정 (예: 16)
		int size = 1_000_000;
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = random(n);
		}
		Map<Integer, Integer> map = countingNumbers(arr);
		map.forEach((num, count) -> System.out.println("num: " + num + ", count: " + count));
		// count가 가장 큰 num을 탐색
		map.entrySet().stream()
			.max(Map.Entry.comparingByValue())
			.ifPresent(entry -> System.out.println("maxNum: " + entry.getKey() + ", count: " + entry.getValue()));

		// count 평균
		map.values().stream()
			.mapToInt(i -> i)
			.average()
			.ifPresent(avg -> System.out.println("average: " + avg));
	}

	// 각 정수들의 등장 횟수를 카운팅한다
	public static Map<Integer, Integer> countingNumbers(int[] arr) {
		return Arrays.stream(arr)
			.boxed()
			.collect(Collectors.toMap(
				Function.identity(),
				v -> 1,
				Integer::sum
			));
	}
}
