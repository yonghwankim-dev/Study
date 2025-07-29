package com.nemo.effective_java.item05.step01;

import java.util.List;

public class SpellChecker {
	private static final Lexicon dictionary = new Lexicon();

	private SpellChecker() {
		throw new AssertionError();
	}

	public static boolean isValid(String word) {
		return dictionary.contains(word);
	}

	public static List<String> suggestions(String typo) {
		return dictionary.suggestionsFor(typo);
	}
}
