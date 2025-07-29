package com.nemo.effective_java.item05.step03;

import java.util.List;

public class SpellChecker {
	private final Lexicon dictionary;

	public SpellChecker(Lexicon dictionary) {
		this.dictionary = dictionary;
	}

	public boolean isValid(String word) {
		return dictionary.contains(word);
	}

	public List<String> suggestions(String typo) {
		return dictionary.suggestionsFor(typo);
	}
}
