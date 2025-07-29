package com.nemo.effective_java.item31.step02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		Stack<Number> stack = new Stack<>();
		Iterable<Integer> integers = List.of(1, 2, 3);
		stack.pushAll(integers);

		Collection<Object> objects = new ArrayList<>();
		stack.popAll(objects);
	}
}
