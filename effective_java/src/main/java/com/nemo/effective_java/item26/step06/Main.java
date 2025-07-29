package com.nemo.effective_java.item26.step06;

import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Set<String> set1 = Set.of("kim", "lee");
		Set<Integer> set2 = Set.of(1, 2);
		int common = numElementsInCommon(set1, set2);
		System.out.println(common);

		Set<?> set3 = Set.of("kim", "lee", "park");
		common = numElementsInCommon(set1, set3);
		System.out.println(common);
		// set3.add("choi"); // CompileError, set3 is readonly

		Set<? extends String> set4 = Set.of("kim"); // only String subclass object
		// set4.add("choi"); // CompileError
	}

	private static int numElementsInCommon(Set<?> s1, Set<?> s2) {
		int result = 0;
		for (Object o1 : s1) {
			if (s2.contains(o1)) {
				result++;
			}
		}
		return result;
	}
}
