package com.nemo.effective_java.item31.step06;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4, 5));
		swap(integers, 0, 4);
		System.out.println(integers);
	}

	public static void swap(List<?> list, int i, int j) {
		// list.set(i, list.set(j, list.get(i))); // CompileError
	}
}
