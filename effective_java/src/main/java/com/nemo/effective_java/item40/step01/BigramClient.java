package com.nemo.effective_java.item40.step01;

import java.util.HashSet;
import java.util.Set;

public class BigramClient {
	public static void main(String[] args) {
		Set<Bigram> set = new HashSet<>();

		for (int i = 0; i < 10; i++) {
			for (char ch = 'a'; ch <= 'z'; ch++) {
				set.add(new Bigram(ch, ch));
			}
		}
		System.out.println(set.size()); // expected: 26, actual: 260
	}
}
