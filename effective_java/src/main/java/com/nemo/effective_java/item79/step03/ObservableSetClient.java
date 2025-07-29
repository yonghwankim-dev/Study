package com.nemo.effective_java.item79.step03;

import java.util.HashSet;

public class ObservableSetClient {
	public static void main(String[] args) {
		ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

		set.addObserver((s, element) -> System.out.println(element));

		for (int i = 0; i < 100; i++) {
			set.add(i);
		}
	}
}
