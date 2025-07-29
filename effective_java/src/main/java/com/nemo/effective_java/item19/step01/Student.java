package com.nemo.effective_java.item19.step01;

public class Student {
	private final String name;
	public Student(String name) {
		this.name = name;
	}

	/**
	 * Returns the type of student.
	 * This method can be overridden by subclasses to specify the type of student.
	 *
	 * @return a {@code String} representing the student type.
	 *         The default implementation returns "Regular Student".
	 */
	public String getStudentType(){
		return "Regular Student";
	}

	/**
	 * Prints the details of the student, including the student name, student type and enrollment status.
	 *
	 * @implSpec
	 * This method calls the {@link #getStudentType()} method, which is overridable.
	 * Therefore, if a subclass overrides {@code getStudentType()}, the overridden
	 * version will be called when this method is invoked.
	 *
	 * <p>
	 * Note to subclass implementers: If you override {@code getStudentType()},
	 * be aware that {@code printStudentDetails()} will call your overridden method.
	 * </p>
	 */
	public void printStudentDetails(){
		System.out.println("Student Name: " + name);
		System.out.println("Student Type: " + getStudentType());
		System.out.println("Enrollment Status: Enrolled");
	}
}
