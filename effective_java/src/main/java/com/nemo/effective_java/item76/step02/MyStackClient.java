package com.nemo.effective_java.item76.step02;

public class MyStackClient {
	public static void main(String[] args) {
		MyStack myStack = new MyStack(10);
		myStack.push(1); // size = 1
		System.out.println(myStack.pop()); // size = 0
		try {
			System.out.println(myStack.pop()); // IllegalStateException
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(myStack.size()); // size = 0
	}
}
