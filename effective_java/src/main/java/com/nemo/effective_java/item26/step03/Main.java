package com.nemo.effective_java.item26.step03;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> strings = new ArrayList<>();
		unsafeAdd(strings, Integer.valueOf(42));
		String s = strings.get(0); // RuntimeError: ClassCastException
	}

	private static void unsafeAdd(List list, Object o) {
		list.add(o);
	}
}
