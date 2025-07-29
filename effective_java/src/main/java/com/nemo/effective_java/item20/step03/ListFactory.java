package com.nemo.effective_java.item20.step03;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

public class ListFactory {
	static List<Integer> intArrayAsList(int[] arr) {
		Objects.requireNonNull(arr);

		return new AbstractList<>() {


			@Override
			public Integer get(int index) {
				return arr[index];
			}

			@Override
			public Integer set(int index, Integer val) {
				int oldVal = arr[index];
				arr[index] = val;
				return oldVal;
			}

			@Override
			public int size() {
				return arr.length;
			}
		};
	}

	public static void main(String[] args) {
		List<Integer> list = intArrayAsList(new int[] {1, 2, 3, 4, 5});
		System.out.println(list.set(0, 6));
		System.out.println(list.get(0));
		System.out.println(list.size());
	}
}
