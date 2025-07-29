package com.nemo.effective_java.item05.step04;

public class Tile {
	private final String color;

	public Tile(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return String.format("Tile(color=%s)", color);
	}
}
