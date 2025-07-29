package com.nemo.effective_java.item57.step01;

import java.util.Iterator;
import java.util.List;

public class StudentClient {
	public static void main(String[] args) {
		List<Student> students = List.of(new Student("kim"), new Student("lee"), new Student("park"));

		// recommend code
		for (Student student : students) {
			System.out.println(student);
		}

		// Code when an Iterator is needed
		for (Iterator<Student> i = students.iterator(); i.hasNext(); ) {
			Student student = i.next();
			System.out.println(student);
		}
	}
}
