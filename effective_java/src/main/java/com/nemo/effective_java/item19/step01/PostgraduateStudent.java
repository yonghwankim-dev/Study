package com.nemo.effective_java.item19.step01;

public class PostgraduateStudent extends Student{

	public PostgraduateStudent(String name) {
		super(name);
	}

	// override method
	@Override
	public String getStudentType() {
		return "Postgraduate Student";
	}

	@Override
	public void printStudentDetails() {
		System.out.println("** Postgraduate Student Details **");
		super.printStudentDetails(); // Calling the method of the parent class
		System.out.println("Thesis Status: Pending Approval");
	}
}
