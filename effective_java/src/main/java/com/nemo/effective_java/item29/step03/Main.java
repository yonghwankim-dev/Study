package com.nemo.effective_java.item29.step03;

public class Main {
	public static void main(String[] args) {
		Stack<String> stack = new Stack<>();
		for(String arg : args) {
			stack.push(arg);
		}
		while (!stack.isEmpty()) {
			System.out.println(stack.pop().toUpperCase());
		}
	}
}
