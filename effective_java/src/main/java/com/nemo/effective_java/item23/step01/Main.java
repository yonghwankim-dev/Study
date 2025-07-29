package com.nemo.effective_java.item23.step01;

public class Main {
	public static void main(String[] args) {
		Figure circle = new Figure(5.0);
		System.out.println(circle.area());

		Figure rectangle = new Figure(3, 4);
		System.out.println(rectangle.area());
	}
}
