package com.nemo.effective_java.item31.step01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		Stack<Number> stack = new Stack<>();
		Iterable<Integer> integers = List.of(1, 2, 3);
		// stack.pushAll(integers); // CompileError, Iterable<Integer> is not subtype of Stack<Number>

		Collection<Object> list = new ArrayList<>();
		// stack.popAll(list); // CompileError, Collection<Object> is not superType of Stack<Number>
	}
}
