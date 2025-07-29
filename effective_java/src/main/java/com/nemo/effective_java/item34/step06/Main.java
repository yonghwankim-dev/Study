package com.nemo.effective_java.item34.step06;

import java.util.Objects;

public class Main {
	public static void main(String[] args) {
		Operation operation = Operation.fromString("+").orElse(null);
		System.out.println(Objects.requireNonNull(operation).apply(1, 2));
	}
}
