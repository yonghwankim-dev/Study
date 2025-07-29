package com.nemo.effective_java.item81.step01;

import java.util.concurrent.ConcurrentHashMap;

public class PerformanceClient {

	private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		final int NUM_STRINGS = 1_000_000;

		long start, end;

		// using the String.intern()
		start = System.currentTimeMillis();
		for (int i = 0; i < NUM_STRINGS; i++) {
			String str = "String" + (i % 10);
			String internedStr = str.intern();
		}
		end = System.currentTimeMillis();
		System.out.println("String.intern() Time : " + (end - start) + "ms");

		// custom intern() method
		start = System.currentTimeMillis();
		for (int i = 0; i < NUM_STRINGS; i++) {
			String str = "String" + (i % 10);
			String internedStr = intern(str);
		}
		end = System.currentTimeMillis();
		System.out.println("custom intern() Time : " + (end - start) + "ms");
	}

	private static String intern(String s) {
		String previousValue = map.putIfAbsent(s, s);
		return previousValue == null ? s : previousValue;
	}
}
