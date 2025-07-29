package com.nemo.effective_java.item52.step01;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CollectionClassifier {
	public static String classify(Set<?> set) {
		return "Set";
	}

	public static String classify(List<?> list) {
		return "List";
	}

	public static String classify(Collection<?> c) {
		return "Unknown";
	}

	public static void main(String[] args) {
		Collection<?>[] collections = {
			Set.of("A", "B", "C"),
			List.of(1, 2, 3),
			new HashMap<>().values()
		};

		for (Collection<?> c : collections) {
			System.out.println(classify(c)); // Unknown * 3
		}
	}
}
