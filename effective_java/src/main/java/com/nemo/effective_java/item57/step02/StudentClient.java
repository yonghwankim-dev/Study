package com.nemo.effective_java.item57.step02;

import java.util.Iterator;
import java.util.List;

public class StudentClient {
	public static void main(String[] args) {
		List<Student> students = List.of(new Student("kim"), new Student("lee"), new Student("park"));

		// bad case
		Iterator<Student> i = students.iterator();
		while (i.hasNext()) {
			System.out.println(i.next());
		}

		Iterator<Student> i2 = students.iterator();
		while (i.hasNext()) { // not throw Exception, i.hasNext() return the False
			System.out.println(i2.next());
		}
	}
}
