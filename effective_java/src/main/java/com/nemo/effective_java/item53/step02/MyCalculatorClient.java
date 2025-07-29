package com.nemo.effective_java.item53.step02;

public class MyCalculatorClient {
	public static void main(String[] args) {
		System.out.println(MyCalculator.min(1, 2, 3)); // 1
		System.out.println(MyCalculator.min(1)); // 1
	}
}
