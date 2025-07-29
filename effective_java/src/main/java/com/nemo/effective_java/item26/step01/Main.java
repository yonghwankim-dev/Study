package com.nemo.effective_java.item26.step01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Main {
	private static class Stamp{
		private String name;
	}

	private static class Coin{
		private int value;

		public Coin(int value) {
			this.value = value;
		}
	}

	public static void main(String[] args) {
		Collection stamps = new ArrayList<Stamp>();
		stamps.add(new Coin(100)); // no compile error

		for (Object object : stamps) {
			Stamp stamp = (Stamp)object; // RuntimeError ClassCaseException
			System.out.println(stamp.name);
		}
	}
}
