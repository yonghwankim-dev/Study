package com.nemo.effective_java.item05.step04;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Mosaic {
	private List<Tile> tiles;

	public Mosaic() {
		this.tiles = new ArrayList<>();
	}

	public static Mosaic create(Supplier<? extends Tile> tileFactory) {
		Mosaic mosaic = new Mosaic();
		for (int i = 0; i < 9; i++) {
			mosaic.addTile(tileFactory.get());
		}
		return mosaic;
	}

	public void addTile(Tile tile) {
		this.tiles.add(tile);
	}

	@Override
	public String toString() {
		return String.format("Mosaic(tiles=%s)", tiles);
	}
}
