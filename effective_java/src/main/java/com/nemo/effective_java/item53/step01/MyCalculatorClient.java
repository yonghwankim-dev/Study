package com.nemo.effective_java.item53.step01;

public class MyCalculatorClient {
	public static void main(String[] args) {
		System.out.println(MyCalculator.sum(1, 2, 3, 4, 5)); // 15
		System.out.println(MyCalculator.sum()); // 0
		System.out.println(MyCalculator.min(1, 2, 3)); // 1
		System.out.println(MyCalculator.min()); // IllegalArgumentException
	}
}
