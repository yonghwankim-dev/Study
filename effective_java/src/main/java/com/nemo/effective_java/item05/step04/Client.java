package com.nemo.effective_java.item05.step04;

public class Client {
	public static void main(String[] args) {
		Mosaic redMosaic = Mosaic.create(() -> new Tile("red"));
		Mosaic blueMosaic = Mosaic.create(() -> new Tile("blue"));
		System.out.println(redMosaic);
		System.out.println(blueMosaic);
	}
}
