package com.nemo.effective_java.item39.step04;

import java.util.ArrayList;
import java.util.List;

public class MyTests {

	@ExceptionTest(IndexOutOfBoundsException.class)
	@ExceptionTest(NullPointerException.class)
	public static void doublyBad() {
		List<String> list = new ArrayList<>();
		list.addAll(5, null); // throw IndexOutOfBoundsException or NullPointerException
	}
}
