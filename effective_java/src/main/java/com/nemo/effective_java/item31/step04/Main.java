package com.nemo.effective_java.item31.step04;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<Person> people = List.of(
			new Person("kim", 20),
			new Person("lee", 30),
			new Person("park", 40),
			new Person("choi", 50));
		Person maxOldPerson = max(people);
		System.out.println(maxOldPerson); // Person{name='choi', age=50}

		List<Student> students = List.of(
			new Student("kim", 20, 5),
			new Student("lee", 30, 4),
			new Student("park", 40, 3),
			new Student("choi", 50, 2)
		);
		/**
		 * compileError
		 * The parent type of Student class, Person Class, implemented Compatible<Person>,
		 * but Student Class did not implement Compatible<Student> interface.
		 */
		// Student maxOldStudent = max(students);
		// System.out.println(maxOldStudent);
	}

	private static <E extends Comparable<E>> E max(List<E> list) {
		return list.stream()
			.max(Comparable::compareTo)
			.orElseThrow();
	}
}
