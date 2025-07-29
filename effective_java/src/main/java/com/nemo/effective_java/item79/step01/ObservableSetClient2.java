package com.nemo.effective_java.item79.step01;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

public class ObservableSetClient2 {
	public static void main(String[] args) {
		ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

		set.addObserver(new SetObserver<>() {
			@Override
			public void added(ObservableSet<Integer> s, Integer element) {
				System.out.println(element);
				if (element == 23) {
					s.removeObserver(this);
				}
			}
		});

		try {
			for (int i = 0; i < 100; i++) {
				set.add(i);
			}
		} catch (ConcurrentModificationException e) {
			e.printStackTrace(System.err);
		}
		System.out.println(set);
	}
}
