package com.nemo.effective_java.item57.step03;

import java.util.Iterator;
import java.util.List;

public class StudentClient {
	public static void main(String[] args) {
		List<Student> students = List.of(new Student("kim"), new Student("lee"), new Student("park"));

		for (Iterator<Student> i = students.iterator(); i.hasNext(); ) {
			System.out.println(i.next());
		}

		// for (Iterator<Student> i2 = students.iterator(); i.hasNext(); ) { // Compile Error
		// 	System.out.println(i2.next());
		// }
	}
}
