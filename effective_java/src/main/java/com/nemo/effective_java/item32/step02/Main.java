package com.nemo.effective_java.item32.step02;

public class Main {
	public static void main(String[] args) {
		dangerousMethod("a", "b", "c");
	}

	private static <E> void dangerousMethod(E... varargs) {
		Object[] objects = varargs;
		objects[0] = 42; // RuntimeError : ArrayStoreException;
	}
}
