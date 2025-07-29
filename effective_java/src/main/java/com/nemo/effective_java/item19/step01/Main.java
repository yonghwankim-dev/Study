package com.nemo.effective_java.item19.step01;

public class Main {
	public static void main(String[] args) {
		Student student = new Student("kim");
		student.printStudentDetails();
		System.out.println();

		Student postgraduateStudent = new PostgraduateStudent("lee");
		postgraduateStudent.printStudentDetails();
	}
}
