package com.nemo.effective_java.item59.step01.step01_01;

import java.util.Arrays;
import java.util.Random;

public class RandomGenerator {
	private static Random rnd = new Random();

	public static int random(int n) {
		return Math.abs(rnd.nextInt()) % n;
	}

	public static void main(String[] args) {
		int n = 16;  // n을 2의 제곱수로 설정 (예: 16)
		int[] arr = new int[1000];
		for (int i = 0; i < 1000; i++) {
			arr[i] = random(n);
		}
		findRepeatedSequence(arr);
	}

	private static void findRepeatedSequence(int[] arr) {
		int n = arr.length;

		for (int start1 = 0; start1 < n; start1++) {
			for (int start2 = start1 + 1; start2 < n; start2++) {
				int length = Math.min(n - start1, n - start2);

				if (isSameSequence(arr, start1, start2, length)) {
					System.out.println(
						"반복되는 수열 발견: " + Arrays.toString(Arrays.copyOfRange(arr, start1, start1 + length)));
				}
			}
		}
		System.out.println("반복되는 수열이 없습니다.");
	}

	private static boolean isSameSequence(int[] arr, int start1, int start2, int length) {
		for (int i = 0; i < length; i++) {
			if (arr[start1 + i] != arr[start2 + i]) {
				return false;
			}
		}
		return true;
	}
}
