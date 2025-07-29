package com.nemo.effective_java.item26.step05;

import java.util.HashSet;
import java.util.Set;

// Incorrect Example
public class Main {
	public static void main(String[] args) {
		Set<String> set1 = Set.of("kim", "lee");
		Set<Integer> set2 = Set.of(1, 2);
		int common = numElementsInCommon(set1, set2);
		System.out.println(common);
	}

	private static int numElementsInCommon(Set s1, Set s2) {
		int result = 0;
		for (Object o1 : s1) {
			if (s2.contains(o1)) {
				result++;
			}
		}
		return result;
	}
}
