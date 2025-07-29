package com.nemo.effective_java.item60.step01;

public class Client {
	public static void main(String[] args) {
		System.out.println(1.03 - 0.42); // expected: 0.61, actual: 0.6100000000000001
		System.out.println(1.00 - 9 * 0.10); // expected: 0.1, actual: 0.09999999999999998
	}
}
