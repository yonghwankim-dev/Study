package com.nemo.effective_java.item64.step01;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetClient {
	public static void main(String[] args) {
		Set<Son> sonSet = new LinkedHashSet<>(); // good
		LinkedHashSet<Son> sonSet2 = new LinkedHashSet<>(); // bad
		printSet(sonSet);
		printSet(sonSet2);
	}

	private static void printSet(Set<Son> set) {
		System.out.println(set);
	}

	static class Son {

	}
}
