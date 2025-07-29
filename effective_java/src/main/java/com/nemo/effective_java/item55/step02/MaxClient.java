package com.nemo.effective_java.item55.step02;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MaxClient {
	public static void main(String[] args) {
		List<Integer> numbers = List.of(1, 2, 3, 4, 5);
		System.out.println(max(numbers).orElse(-1));
	}

	public static <E extends Comparable<E>> Optional<E> max(Collection<E> collection) {
		if (collection.isEmpty()) {
			return Optional.empty();
		}

		E result = null;
		for (E e : collection) {
			if (result == null || e.compareTo(result) > 0) {
				result = e;
			}
		}

		return Optional.of(result);
	}
}
