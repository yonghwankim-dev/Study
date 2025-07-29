package com.nemo.effective_java.item01.step04;

public class CarClient {
	public static void main(String[] args) {
		Car car = Car.defaultCar();
		System.out.println(car.getName());
		car.drive();
	}
}
