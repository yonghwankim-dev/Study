package com.nemo.effective_java.item07.step01;

public class StackClient {
	public static void main(String[] args) {
		Stack stack = new Stack();
		stack.push("Hello");
		stack.push("World");
		System.out.println(stack.pop());
		System.out.println(stack);
	}
}
