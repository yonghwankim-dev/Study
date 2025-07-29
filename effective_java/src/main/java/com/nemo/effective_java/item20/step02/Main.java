package com.nemo.effective_java.item20.step02;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<Student> studentList = new ArrayList<>();
		studentList.add(new Student("Alice", 20));
		studentList.add(new Student("Bob", 22));
		studentList.add(new Student("Charlie", 18));

		StudentListWrapper wrappedStudentList = new StudentListWrapper(studentList);

		wrappedStudentList.printAllStudents();

		wrappedStudentList.printStudentsAboveAge(19);

		wrappedStudentList.addStudent(new Student("Dave", 25));
		wrappedStudentList.removeStudent(new Student("Charlie", 18));

		wrappedStudentList.printAllStudents();
	}
}
