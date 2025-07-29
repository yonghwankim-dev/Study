package com.nemo.effective_java.item57.step02;

public class Student {
	private final String name;

	public Student(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
