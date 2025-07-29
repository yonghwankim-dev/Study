package com.nemo.effective_java.item31.step05;

public class Student extends Person {
	private final int studentNumber;

	public Student(String name, int age, int studentNumber) {
		super(name, age);
		this.studentNumber = studentNumber;
	}

	@Override
	public String toString() {
		return String.format("studentNumber=%d, person=%s", studentNumber, super.toString());
	}
}
