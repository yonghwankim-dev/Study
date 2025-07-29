package com.nemo.effective_java.item23.step01;

public class Figure {
	enum Shape { RECTANGLE, CIRCLE };

	// tag field
	private final Shape shape;

	private double length;
	private double width;

	private double radius;

	public Figure(double radius){
		shape = Shape.CIRCLE;
		this.radius = radius;
	}

	public Figure(double length, double width){
		shape = Shape.RECTANGLE;
		this.length = length;
		this.width = width;
	}

	public double area(){
		return switch (shape) {
			case RECTANGLE -> length * width;
			case CIRCLE -> Math.PI * (radius * radius);
		};
	}
}
