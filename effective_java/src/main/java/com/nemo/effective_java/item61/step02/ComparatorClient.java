package com.nemo.effective_java.item61.step02;

import java.util.Comparator;

public class ComparatorClient {
	public static void main(String[] args) {
		Comparator<Integer> naturalOrder = (iBoxed, jBoxed) -> {
			int i = iBoxed;
			int j = jBoxed;
			return (i < j) ? -1 : (i == j ? 0 : 1);
		};
		System.out.println(naturalOrder.compare(new Integer(42), new Integer(42)));
	}
}
