package com.nemo.effective_java.item29.step02;

public class Main {
	public static void main(String[] args) {
		Stack<String> stack = new Stack<>();
		stack.push("a");
		stack.push("b");

		String pop = stack.pop();
		System.out.println(pop);
		System.out.println(stack.isEmpty());
	}
}
