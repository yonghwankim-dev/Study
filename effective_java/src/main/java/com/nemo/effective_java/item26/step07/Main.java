package com.nemo.effective_java.item26.step07;

import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Set<String> names = Set.of("kim", "lee", "park");
		if (names instanceof Set) {
			Set<?> s = (Set<?>) names;
			System.out.println(s);
		}else{
			System.out.println("Not a Set");
		}

		Set<?> names2 = Set.of("kim", "lee", "park");
		if (names2 instanceof Set<?>) {
			Set<?> s = (Set<?>) names2;
			System.out.println(s);
		}else{
			System.out.println("Not a Set");
		}
	}
}
