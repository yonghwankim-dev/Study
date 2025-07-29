package com.nemo.effective_java.item58.step01;

import java.util.Iterator;
import java.util.List;

public class StudentClient {
	public static void main(String[] args) {
		List<Student> students = List.of(new Student("kim"), new Student("lee"), new Student("park"));

		for (Iterator<Student> i = students.iterator(); i.hasNext(); ) {
			System.out.print(i.next() + " ");
		}
		System.out.println();

		for (int i = 0; i < students.size(); i++) {
			System.out.print(students.get(i) + " ");
		}
		System.out.println();

		// recommend code
		for (Student student : students) {
			System.out.print(student + " ");
		}
	}
}
