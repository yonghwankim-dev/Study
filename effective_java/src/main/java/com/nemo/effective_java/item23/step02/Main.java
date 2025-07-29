package com.nemo.effective_java.item23.step02;

public class Main {
	public static void main(String[] args) {
		Figure rectangle = new Rectangle(10, 20);
		Figure circle = new Circle(5);
		Figure square = new Square(4);

		System.out.println("Rectangle area: " + rectangle.area());
		System.out.println("Circle area: " + circle.area());
		System.out.println("Square area: " + square.area());
	}
}
