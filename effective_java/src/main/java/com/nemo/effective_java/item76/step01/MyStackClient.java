package com.nemo.effective_java.item76.step01;

public class MyStackClient {
	public static void main(String[] args) {
		MyStack myStack = new MyStack(10);
		myStack.push(1); // size = 1
		System.out.println(myStack.pop()); // size = 0
		try {
			System.out.println(myStack.pop()); // ArrayIndexOutOfBoundsException
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(myStack.size()); // -1, Despite the exception occurring, the size is not zero
	}
}
