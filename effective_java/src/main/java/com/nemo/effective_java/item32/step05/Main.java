package com.nemo.effective_java.item32.step05;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> firends = List.of("kim", "lee", "park");
		List<String> romans = List.of("west roman", "east roman", "north roman");
		List<String> countrymen = List.of("a", "b", "c");
		List<String> strings = flatten(List.of(firends, romans, countrymen));
		System.out.println(strings);
	}

	private static <T> List<T> flatten(List<List<? extends T>> lists) {
		List<T> result = new ArrayList<>();
		for (List<? extends T> list : lists) {
			result.addAll(list);
		}
		return result;
	}
}
