package com.nemo.effective_java.item39.step03;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 명시한 예외를 던저야만 성공하는 테스트 메서드용 애노테이션
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
	Class<? extends Throwable>[] value();
}
