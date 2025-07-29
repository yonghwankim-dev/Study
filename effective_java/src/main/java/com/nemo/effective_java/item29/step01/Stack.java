package com.nemo.effective_java.item29.step01;

import java.util.EmptyStackException;

public class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			Object[] oldElements = elements;
			elements = new Object[2 * size + 1];
			System.arraycopy(oldElements, 0, elements, 0, size);
		}
	}

	public Object pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		Object result = elements[--size];
		elements[size] = null; // Eliminate obsolete reference
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}
}
