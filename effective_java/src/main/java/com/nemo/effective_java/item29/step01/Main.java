package com.nemo.effective_java.item29.step01;

public class Main {
	public static void main(String[] args) {
		Stack stack = new Stack();
		stack.push("a");
		stack.push("b");

		String pop = (String)stack.pop();
		System.out.println(pop);

		System.out.println(stack.isEmpty());

		stack.push(1);
		try{
			pop = (String)stack.pop(); // RuntimeError: ClassCastException
		}catch (ClassCastException e){
			System.out.println(e);
		}
		System.out.println(pop);
	}
}
