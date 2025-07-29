package com.nemo.effective_java.item38.step01;

import java.util.Arrays;
import java.util.Collection;

public class OperationClient {
	public static void main(String[] args) {
		Operation operation = BasicOperation.PLUS;
		double sum = operation.apply(1, 2);
		System.out.println("sum = " + sum);

		operation = Extendedoperation.REMAINDER;
		double remain = operation.apply(4, 2);
		System.out.println("remain = " + remain);

		test(Extendedoperation.class, 4, 2);
		test2(Arrays.asList(Extendedoperation.values()), 4, 2);
	}

	private static <T extends Enum<T> & Operation> void test(Class<T> opEnumType, double x, double y) {
		for (Operation op : opEnumType.getEnumConstants()) {
			System.out.printf("%.2f %s %.2f = %.2f%n", x, op, y, op.apply(x, y));
		}
	}

	private static void test2(Collection<? extends Operation> operations, double x, double y) {
		for (Operation op : operations) {
			System.out.printf("%.2f %s %.2f = %.2f%n", x, op, y, op.apply(x, y));
		}
	}
}
