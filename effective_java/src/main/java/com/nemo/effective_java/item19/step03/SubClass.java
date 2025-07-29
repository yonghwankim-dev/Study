package com.nemo.effective_java.item19.step03;

public class SubClass extends SuperClass{
	private static final long serialVersionUID = 1L;
	private int value;

	public SubClass(String name, int value) {
		super(name);
		this.value = value;
	}

	@Override
	protected void afterRead() {
		System.out.println("SubClass: afterRead() called with value = " + value);
	}

	@Override
	public String toString() {
		return "SubClass{name='" + super.toString() + "', value=" + value + "}";
	}
}
