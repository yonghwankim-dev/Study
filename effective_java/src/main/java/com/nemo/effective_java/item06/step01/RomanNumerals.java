package com.nemo.effective_java.item06.step01;

public class RomanNumerals {

	public static boolean isRomanNumeral(String s) {
		return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
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
