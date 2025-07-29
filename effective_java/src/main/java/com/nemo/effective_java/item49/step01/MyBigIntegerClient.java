package com.nemo.effective_java.item49.step01;

import java.math.BigInteger;

public class MyBigIntegerClient {
	public static void main(String[] args) {
		MyBigInteger myBigInteger = new MyBigInteger(10);
		System.out.println(myBigInteger.mod(BigInteger.valueOf(3L)));

		try {
			System.out.println(myBigInteger.mod(BigInteger.ZERO));
		} catch (ArithmeticException e) {
			System.err.println("Cause ArithmeticException");
		}
	}
}
