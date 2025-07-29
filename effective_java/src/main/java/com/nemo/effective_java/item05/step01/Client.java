package com.nemo.effective_java.item05.step01;

public class Client {
	public static void main(String[] args) {
		System.out.println(SpellChecker.isValid("orange"));
		System.out.println(SpellChecker.suggestions("ornage"));
	}
}
