package com.nemo.effective_java.item32.step04;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> strings1 = List.of("a", "b", "c");
		List<String> strings2 = List.of("d", "e", "f");
		List<String> strings = flatten(strings1, strings2);
		System.out.println(strings);
	}

	@SafeVarargs
	private static <T> List<T> flatten(List<? extends T>... lists) {
		List<T> result = new ArrayList<>();
		for (List<? extends T> list : lists) {
			result.addAll(list);
		}
		return result;
	}
}
