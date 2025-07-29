package com.nemo.effective_java.item04;

public class UtilityClass {
	private UtilityClass() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		UtilityClass utilityClass = new UtilityClass();
		System.out.println(utilityClass);
	}
}
