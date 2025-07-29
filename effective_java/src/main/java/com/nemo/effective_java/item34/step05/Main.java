package com.nemo.effective_java.item34.step05;

public class Main {
	public static void main(String[] args) {
		double x = 2.0;
		double y = 4.0;
		for (Operation op : Operation.values()) {
			System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
		}
	}
}
