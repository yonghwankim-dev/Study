package com.nemo.effective_java.item21.step01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Main {
	public static void main(String[] args) {
		Collection<Integer> collection = Collections.synchronizedCollection(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
		boolean result = collection.removeIf(n -> n % 2 == 0);
		System.out.printf("collection=%s, result=%b", collection, result);
	}
}
