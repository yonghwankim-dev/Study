package com.nemo.effective_java.item81.step01;

import java.util.concurrent.ConcurrentHashMap;

public class Client {

	private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		// String 클래스의 intern() 메서드는 문자열 상수 풀에 문자열을 추가하거나, 이미 존재하는 문자열을 반환하는 메서드입니다.
		System.out.println("hello".intern()); // hello
		System.out.println(intern("hello")); // hello
		System.out.println(intern("hello")); // hello
	}

	// String 클래스의 intern 메서드를 흉내낸 메서드
	public static String intern(String s) {
		String previousValue = map.putIfAbsent(s, s);
		return previousValue == null ? s : previousValue;
	}
}
