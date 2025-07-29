package com.nemo.effective_java.item09.step03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FruitFileReader {
	public static String firstLineOfFile(String path, String defaultFruit) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return br.readLine();
		} catch (IOException e) {
			return defaultFruit;
		}
	}

	public static void main(String[] args) {
		String path = "src/main/resources/fruits.txt";
		String defaultFruit = "banana";
		String firstFruitOfFile = firstLineOfFile(path, defaultFruit);
		System.out.println(firstFruitOfFile);
	}
}
