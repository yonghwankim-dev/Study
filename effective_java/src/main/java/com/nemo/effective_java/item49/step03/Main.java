package com.nemo.effective_java.item49.step03;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		long[] arr = {5, 4, 3, 2, 1};
		sort(arr, 2, arr.length);
		System.out.println(Arrays.toString(arr)); // expected output : 5, 4, 1, 2, 3

		sort(arr, -1, 0);
	}

	private static void sort(long[] a, int offset, int length) {
		assert a != null;
		assert offset >= 0 && offset <= a.length;
		assert length >= 0 && length <= a.length - offset;

		// sort algorithm
		Arrays.sort(a, offset, length);
	}
}
