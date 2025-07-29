package com.nemo.effective_java.item29.step02;

import java.util.EmptyStackException;

public class Stack<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	@SuppressWarnings("unchecked")
	public Stack() {
		elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}

	@SuppressWarnings("unchecked")
	private void ensureCapacity() {
		if (elements.length == size) {
			Object[] oldElements = elements;
			elements = (E[])new Object[2 * size + 1];
			System.arraycopy(oldElements, 0, elements, 0, size);
		}
	}

	public E pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		E result = elements[--size];
		elements[size] = null; // Eliminate obsolete reference
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}
}
