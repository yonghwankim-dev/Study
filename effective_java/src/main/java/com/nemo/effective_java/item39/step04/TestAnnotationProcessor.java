package com.nemo.effective_java.item39.step04;

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
			if (method.isAnnotationPresent(ExceptionTest.class) || method.isAnnotationPresent(
				ExceptionTestContainer.class)) {
				tests++;
				try {
					method.invoke(null);
					System.out.printf("test %s fail: not throw exception%n", method);
				} catch (InvocationTargetException e) {
					Throwable actual = e.getCause();
					int oldPassed = passed;
					ExceptionTest[] exceptionTests = method.getAnnotationsByType(ExceptionTest.class);
					for (ExceptionTest exceptionTest : exceptionTests) {
						if (exceptionTest.value().isInstance(actual)) {
							passed++;
							break;
						}
					}
					if (passed == oldPassed) {
						System.out.printf("test %s fail: %s%n", method, actual);
					}
				} catch (Exception e) {
					System.out.println("Misused @ExceptionTest: " + method);
				}
			} else {
				System.out.println(method + " is not exception test");
			}
		}

		System.out.printf("Test Summary: success: %d, fail: %d%n", passed, tests - passed);
	}
}
