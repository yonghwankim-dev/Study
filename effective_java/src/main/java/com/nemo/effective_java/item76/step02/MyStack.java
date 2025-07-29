package com.nemo.effective_java.item76.step02;

public class MyStack {
	private final Integer[] elements;
	private int size;

	public MyStack(int capacity) {
		this.elements = new Integer[capacity];
		this.size = 0;
	}

	public void push(int value) {
		elements[size++] = value;
	}

	public int pop() {
		if (size == 0) {
			throw new IllegalStateException("Stack is empty");
		}
		int result = elements[--size];
		elements[size] = null;
		return result;
	}

	public int size() {
		return size;
	}
}
