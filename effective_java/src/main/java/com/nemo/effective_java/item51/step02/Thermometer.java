package com.nemo.effective_java.item51.step02;

public class Thermometer {

	private TemperatureScale scale;

	private Thermometer(TemperatureScale scale) {
		this.scale = scale;
	}

	public static Thermometer newInstance(TemperatureScale scale) {
		return new Thermometer(scale);
	}

	@Override
	public String toString() {
		return scale.name();
	}
}
