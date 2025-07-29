package com.nemo.effective_java.item01.step03;

public class CarClient {
	public static void main(String[] args) {
		Car car = Cars.defaultCar();
		System.out.println(car.getName());
		car.drive();
	}
}
