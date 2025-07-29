package com.nemo.effective_java.item39.step01;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class TestAnnotationProcessor {

	private TestAnnotationProcessor() {
	}

	public static void processTests(Class<?> testClass) {
		int tests = 0;
		int passed = 0;

		// get all method in testClass
		Method[] methods = testClass.getDeclaredMethods();

		for (Method method : methods) {
			// check @Test annotation in method
			if (method.isAnnotationPresent(Test.class)) {
				tests++;
				try {
					method.invoke(null); // Call without object because it is a static method, and no argument
					passed++;
				} catch (InvocationTargetException e) {
					System.out.println(method + " failed: " + e.getCause());
				} catch (Exception e) {
					System.out.println("Misused @Test: " + method);
				}
			} else {
				System.out.println(method + " is not test");
			}
		}

		System.out.printf("Test Summary: success: %d, fail: %d%n", passed, tests - passed);
	}
}
