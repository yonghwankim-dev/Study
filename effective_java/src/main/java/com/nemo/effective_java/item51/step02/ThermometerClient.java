package com.nemo.effective_java.item51.step02;

public class ThermometerClient {
	public static void main(String[] args) {
		Thermometer thermometer = Thermometer.newInstance(TemperatureScale.CELSIUS);
		System.out.println(thermometer);
	}
}
