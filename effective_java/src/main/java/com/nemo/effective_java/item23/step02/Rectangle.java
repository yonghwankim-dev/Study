package com.nemo.effective_java.item23.step02;

public class Rectangle extends Figure {
	private final double length;
	private final double width;

	public Rectangle(double length, double width) {
		this.length = length;
		this.width = width;
	}

	@Override
	double area() {
		return length * width;
	}
}
