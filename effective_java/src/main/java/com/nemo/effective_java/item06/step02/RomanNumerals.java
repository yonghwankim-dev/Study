package com.nemo.effective_java.item06.step02;

import java.util.regex.Pattern;

public class RomanNumerals {
	private static final Pattern ROMAN = Pattern.compile(
		"^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

	public static boolean isRomanNumeral(String s) {
		return ROMAN.matcher(s).matches();
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int count = 1_000_000;
		for (int i = 0; i < count; i++) {
			isRomanNumeral("MCMLXXVI");
		}
		long end = System.currentTimeMillis() - start;
		System.out.println("Elapsed time: " + end + " ms");
	}
}
