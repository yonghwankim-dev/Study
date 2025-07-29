package com.nemo.effective_java.item01.step04;

public interface Car {
	String getName();

	void drive();

	static Car defaultCar() {
		return new Car() {
			@Override
			public String getName() {
				return "k3";
			}

			@Override
			public void drive() {
				System.out.println("k3 is driving");
			}
		};
	}
}
