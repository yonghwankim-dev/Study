package com.nemo.effective_java.item61.step03;

public class IntegerCompareClient {
	private static Integer i;

	public static void main(String[] args) {
		if (i == 42) { // RuntimeError: NullPointerException
			System.out.println("unbelievable!!");
		}
	}
}
