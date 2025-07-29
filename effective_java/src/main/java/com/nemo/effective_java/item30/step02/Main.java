package com.nemo.effective_java.item30.step02;

import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Set<String> set1 = Set.of("a", "b");
		Set<String> set2 = Set.of("a","c", "d");
		Set<String> union = union(set1, set2);
		System.out.println(union); // [a, b, c, d]
	}

	public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> result = new HashSet<>(s1);
		result.addAll(s2);
		return result;
	}
}
