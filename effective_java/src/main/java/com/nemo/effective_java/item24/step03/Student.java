package com.nemo.effective_java.item24.step03;

public class Student {
	private final String name;

	public Student(String name) {
		this.name = name;
	}

	public Runnable sayName() {
		return new Runnable() {
			@Override
			public void run() {
				System.out.println("My name is " + name);
			}
		};
	}
}
