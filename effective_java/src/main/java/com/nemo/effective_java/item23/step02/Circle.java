package com.nemo.effective_java.item23.step02;

public class Circle extends Figure {
	private final double radius;

	public Circle(double radius) {
		this.radius = radius;
	}

	@Override
	double area() {
		return Math.PI * (radius * radius);
	}
}
