package com.nemo.effective_java.item39.step02;

public class MyTests {
	// this test must success
	@ExceptionTest(ArithmeticException.class)
	public static void m1() {
		int i = 0;
		i = i / i;
	}

	// this test must fail (wrong exception)
	@ExceptionTest(ArithmeticException.class)
	public static void m2() {
		int[] a = new int[0];
		int i = a[1];
	}

	// this test must fail (exception not thrown)
	@ExceptionTest(ArithmeticException.class)
	public static void m3() {
	}
}
