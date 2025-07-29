package com.nemo.effective_java.item26.step04;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> strings = new ArrayList<>();
		// unsafeAdd(strings, Integer.valueOf(42)); // CompileError, List<String> type is not subtype of List<Object>
		String s = strings.get(0);
	}

	private static void unsafeAdd(List<Object> list, Object o) {
		list.add(o);
	}
}
