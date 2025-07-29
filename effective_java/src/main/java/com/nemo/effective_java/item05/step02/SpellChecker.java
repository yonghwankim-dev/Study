package com.nemo.effective_java.item05.step02;

import java.util.List;

public class SpellChecker {
	public static SpellChecker INSTANCE = new SpellChecker();
	private final Lexicon dictionary = new Lexicon();

	private SpellChecker() {

	}

	public boolean isValid(String word) {
		return dictionary.contains(word);
	}

	public List<String> suggestions(String typo) {
		return dictionary.suggestionsFor(typo);
	}
}
