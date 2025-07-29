package com.nemo.effective_java.item81.step02;

import java.util.concurrent.ConcurrentHashMap;

public class Client {
	private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		System.out.println(intern("hello"));
		System.out.println(intern("hello"));
	}

	public static String intern(String s) {
		String result = map.get(s);
		if (result == null) {
			result = map.putIfAbsent(s, s);
			if (result == null) {
				result = s;
			}
		}
		return result;
	}
}
