package com.nemo.effective_java.item56.step01;

import java.util.ArrayList;
import java.util.List;

public class MyCollection {

	private final List<Integer> list = new ArrayList<>();

	/**
	 * returns true if this collection is Empty
	 *
	 * @implSpec
	 * This implementation return {@code this.size() == 0}.
	 *
	 * @return true if this collection is Empty
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	public int size() {
		return list.size();
	}
}
