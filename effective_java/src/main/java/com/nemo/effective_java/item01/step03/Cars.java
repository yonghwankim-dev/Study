package com.nemo.effective_java.item01.step03;

public class Cars {
	private Cars() {
	}

	public static Car defaultCar() {
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
