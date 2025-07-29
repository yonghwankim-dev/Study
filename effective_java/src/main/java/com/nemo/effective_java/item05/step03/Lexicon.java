package com.nemo.effective_java.item05.step03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lexicon {
	private static final Set<String> WORDS = new HashSet<>(Arrays.asList(
		"apple", "banana", "orange", "grape", "watermelon"
	));

	public boolean contains(String word) {
		return WORDS.contains(word);
	}

	public List<String> suggestionsFor(String typo) {
		List<String> suggestions = new ArrayList<>();
		for (String word : WORDS) {
			if (word.startsWith(typo.substring(0, 1))) {
				suggestions.add(word);
			}
		}
		return suggestions;
	}
}
