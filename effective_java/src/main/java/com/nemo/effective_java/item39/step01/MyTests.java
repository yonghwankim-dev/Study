package com.nemo.effective_java.item39.step01;

public class MyTests {
	@Test
	public static void m1() {
		// success the test
	}

	public static void m2() {
		// this method is not test method
	}

	@Test
	public static void m3() {
		// this test method is failed
		throw new RuntimeException("Boom");
	}

	// m4 is not test method
	public static void m4() {
	}

	@Test
	public void m5() {
		// this test method is not static method
	}

	public static void m6() {
		// this test method is not annotated with @Test
	}

	@Test
	public static void m7() {
		// this test method is failed;
		throw new RuntimeException("Boom");
	}

	public static void m8() {
		// this test is not test method
	}
}
