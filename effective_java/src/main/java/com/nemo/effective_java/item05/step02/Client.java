package com.nemo.effective_java.item05.step02;

public class Client {
	public static void main(String[] args) {
		System.out.println(SpellChecker.INSTANCE.isValid("orange"));
		System.out.println(SpellChecker.INSTANCE.suggestions("ornage"));
	}
}
