package com.nemo.effective_java.item05.step03;

public class Client {
	public static void main(String[] args) {
		SpellChecker spellChecker = new SpellChecker(new Lexicon());
		System.out.println(spellChecker.isValid("orange"));
		System.out.println(spellChecker.suggestions("ornage"));
	}
}
