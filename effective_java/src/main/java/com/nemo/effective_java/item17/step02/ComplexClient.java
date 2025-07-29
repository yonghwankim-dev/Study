package com.nemo.effective_java.item17.step02;

public class ComplexClient {
	public static void main(String[] args) {
		Complex complex = Complex.valueOf(1.0, 1.0);
		Complex plus = complex.plus(Complex.valueOf(2.0, 2.0));
		System.out.println(plus);
	}
}
