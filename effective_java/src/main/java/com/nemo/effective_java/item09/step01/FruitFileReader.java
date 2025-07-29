package com.nemo.effective_java.item09.step01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FruitFileReader {
	public static String firstLineOfFile(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			return br.readLine();
		} finally {
			br.close();
		}
	}

	public static void main(String[] args) throws IOException {
		String path = "src/main/resources/fruits.txt";
		String firstFruitOfFile = firstLineOfFile(path);
		System.out.println(firstFruitOfFile);
	}
}
