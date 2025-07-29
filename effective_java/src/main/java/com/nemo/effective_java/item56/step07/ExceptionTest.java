package com.nemo.effective_java.item56.step07;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method is a test method that
 * must throw the designated exception to pass.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
	/**
	 * The exception that the annotated test method must throw in order to pass.
	 * (The test is permitted to throw any subtype of the specified exception.)
	 *
	 * @return the expected exception class
	 */
	Class<? extends Throwable> value();
}
