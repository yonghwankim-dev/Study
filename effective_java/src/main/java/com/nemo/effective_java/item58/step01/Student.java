package com.nemo.effective_java.item58.step01;

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
