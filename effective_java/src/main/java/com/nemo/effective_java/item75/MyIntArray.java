package com.nemo.effective_java.item75;

public class MyIntArray {
	private final int[] array;

	public MyIntArray(int capacity) {
		this.array = new int[capacity];
	}

	public int get(int index) {
		if (index < 0 || index >= array.length) {
			throw new MyIndexOutOfBoundsException(0, array.length, index);
		}
		return array[index];
	}
}
