package com.nemo.effective_java.item33.step03;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class Main {
	public static void main(String[] args) throws NoSuchMethodException {
		Class<?> clazz = MyClass.class;

		Method method = clazz.getMethod("myMethod");
		String annotationTypeName = MyAnnotation.class.getName();
		Annotation annotation = getAnnotation(method, annotationTypeName);

		if (annotation instanceof MyAnnotation myAnnotation) {
			System.out.println("Annotation value: " + myAnnotation.value());
		} else {
			System.out.println("Annotation not found.");
		}
	}

	private static Annotation getAnnotation(AnnotatedElement element, String annotationTypeName) {
		Class<?> annotationType;
		try {
			annotationType = Class.forName(annotationTypeName);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		Class<? extends Annotation> subclass = annotationType.asSubclass(Annotation.class);
		return element.getAnnotation(subclass);
	}
}
