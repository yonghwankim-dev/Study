package com.nemo.effective_java.item55.step03;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MaxClient {
	public static void main(String[] args) {
		List<Integer> numbers = List.of(1, 2, 3, 4, 5);
		System.out.println(max(numbers).orElse(-1));
	}

	public static <E extends Comparable<E>> Optional<E> max(Collection<E> collection) {
		return collection.stream()
			.max(Comparator.naturalOrder());
	}
}
