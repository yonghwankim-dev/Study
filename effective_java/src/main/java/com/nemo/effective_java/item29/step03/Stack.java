package com.nemo.effective_java.item29.step03;

import java.util.EmptyStackException;

public class Stack<E> {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(E e) {
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

	public E pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		@SuppressWarnings("unchecked") E result = (E)elements[--size];
		elements[size] = null; // Eliminate obsolete reference
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}
}
