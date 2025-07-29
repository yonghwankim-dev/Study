package com.nemo.effective_java.item34.step03;

public class Main {
	public static void main(String[] args) {
		double earthWeight = 5.972 * Math.pow(10, 24);
		double mass = earthWeight / Planet.EARTH.surfaceGravity();
		for (Planet p : Planet.values()) {
			System.out.printf("%s에서의 무게는 %.2f이다.%n", p, p.surfaceWeight(mass));
		}
	}
}
