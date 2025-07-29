package com.nemo.effective_java.item24.step02;

public class Student {
	private final int studentId;
	private final String name;
	private final Grade grade;

	public Student(int studentId, String name, String subject, int score) {
		this.studentId = studentId;
		this.name = name;
		this.grade = new Grade(subject, score);
	}

	public void printStudentInfo() {
		System.out.println("Student ID: " + studentId);
		grade.printGrade();
	}

	private class Grade{
		private final String subject;
		private final int score;

		public Grade(String subject, int score) {
			this.subject = subject;
			this.score = score;
		}

		public void printGrade() {
			System.out.println("name : " + Student.this.name);
			System.out.println("Subject: " + subject);
			System.out.println("Score: " + score);

		}
	}
}
