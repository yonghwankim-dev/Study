package com.nemo.effective_java.item55.step01;

import java.util.Collection;
import java.util.List;

public class MaxClient {
	public static void main(String[] args) {
		List<Integer> numbers = List.of(1, 2, 3, 4, 5);
		System.out.println(max(numbers));
	}

	public static <E extends Comparable<E>> E max(Collection<E> collection) {
		if (collection.isEmpty()) {
			throw new IllegalArgumentException("At least one element is required");
		}

		E result = null;
		for (E e : collection) {
			if (result == null || e.compareTo(result) > 0) {
				result = e;
			}
		}

		return result;
	}
}
