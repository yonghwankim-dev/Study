package com.nemo.effective_java.item32.step01;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> strings1 = List.of("a", "b", "c");
		List<String> strings2 = List.of("x", "y", "z");
		dangerousMethod(strings1, strings2);
	}

	// if generic and varargs mixed, broken type stability
	private static void dangerousMethod(List<String>... stringLists) {
		List<Integer> intList = List.of(42);
		Object[] objects = stringLists;
		objects[0] = intList; // Hip contamination, stringLists[0] -> List<Integer>
		String string = stringLists[0].get(0); // RuntimeError: ClassCastException
		System.out.println(string);
	}
}
