package com.nemo.effective_java.item52.step03;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CollectionClassifier {
	public static String classify(Collection<?> c) {
		if (c instanceof Set) {
			return "Set";
		} else if (c instanceof List) {
			return "List";
		}
		return "Unknown";
	}

	public static void main(String[] args) {
		Collection<?>[] collections = {
			Set.of("A", "B", "C"),
			List.of(1, 2, 3),
			new HashMap<>().values()
		};

		for (Collection<?> c : collections) {
			System.out.println(classify(c)); // Set, List, Unknown
		}
	}
}
