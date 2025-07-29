package com.nemo.effective_java.item31.step05;

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
		Student maxOldStudent = max(students);
		System.out.println(maxOldStudent); // studentNumber=2, person=Person{name='choi', age=50}
	}

	private static <E extends Comparable<? super E>> E max(List<E> list) {
		return list.stream()
			.max(Comparable::compareTo)
			.orElseThrow();
	}
}
