package com.nemo.effective_java.item56.step02;

public class GeometricSeriesCalculatorClient {
	public static void main(String[] args) {
		GeometricSeriesCalculator calculator = new GeometricSeriesCalculator();
		System.out.println(calculator.sumOfGeometricSeries(1, 0.5));
		System.out.println(calculator.sumOfGeometricSeries(1, 0.9));
	}
}
