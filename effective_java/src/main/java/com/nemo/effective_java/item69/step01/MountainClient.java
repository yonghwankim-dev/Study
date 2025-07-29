package com.nemo.effective_java.item69.step01;

public class MountainClient {
	public static void main(String[] args) {
		Mountain[] range = new Mountain[10];
		for (int i = 0; i < range.length; i++) {
			range[i] = new Mountain();
		}
		try {
			int i = 0;
			while (true) {
				range[i++].climb();
			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}
}
