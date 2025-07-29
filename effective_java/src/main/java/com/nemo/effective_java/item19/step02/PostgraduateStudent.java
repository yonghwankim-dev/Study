package com.nemo.effective_java.item19.step02;

public class PostgraduateStudent extends Student {

	private final String name;

	public PostgraduateStudent(String name) {
		super();
		this.name = name;
	}

	@Override
	public void sayName() {
		System.out.println("hello my name is " + name);
	}
}
