package com.nemo.effective_java.item18.step01;

public class HashSetClient {
	public static void main(String[] args) {
		InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
		s.addAll(java.util.Arrays.asList("Snap", "Crackle", "Pop"));
		System.out.println(s.getAddCount()); // expected : 3, actual : 6
	}
}
