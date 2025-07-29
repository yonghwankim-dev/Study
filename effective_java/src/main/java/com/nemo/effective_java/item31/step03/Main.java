package com.nemo.effective_java.item31.step03;

import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Set<Integer> integers = Set.of(1, 3, 5);
		Set<Double> doubles = Set.of(2.0, 4.0, 6.0);
		Set<Number> numbers = union(integers, doubles);
		System.out.println(numbers);
	}

	public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
		Set<E> result = new HashSet<>(s1);
		result.addAll(s2);
		return result;
	}
}
