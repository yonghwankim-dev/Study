package com.nemo.effective_java.item26.step02;

import java.util.ArrayList;
import java.util.Collection;

public class Main {
	private static class Stamp{
		private String name;

		public Stamp(String name) {
			this.name = name;
		}
	}

	private static class Coin{
		private int value;

		public Coin(int value) {
			this.value = value;
		}
	}

	public static void main(String[] args) {
		Collection<Stamp> stamps = new ArrayList<>();
		// stamps.add(new Coin(100)); // compile error
		stamps.add(new Stamp("a"));
		for (Stamp stamp : stamps) { // no ClassCastException
			System.out.println(stamp.name);
		}
	}
}
