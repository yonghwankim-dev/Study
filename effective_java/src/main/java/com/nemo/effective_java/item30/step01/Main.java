package com.nemo.effective_java.item30.step01;

import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Set set1 = Set.of("a", "b");
		Set set2 = Set.of("a","c", "d");
		Set union = union(set1, set2);
		System.out.println(union); // [a, b, c, d]
	}

	public static Set union(Set s1, Set s2) {
		Set result = new HashSet(s1); // unchecked call
		result.addAll(s2); // unchecked call
		return result;
	}
}
