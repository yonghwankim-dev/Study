package com.nemo.effective_java.item24.step03;

public class Main {
	public static void main(String[] args) {
		Student student = new Student("yonghwankim");
		Runnable runnable = student.sayName();
		runnable.run();
	}
}
