package com.nemo.effective_java.item39.step03;

import java.util.ArrayList;
import java.util.List;

public class MyTests {

	@ExceptionTest(value = {IndexOutOfBoundsException.class, NullPointerException.class})
	public static void doublyBad() {
		List<String> list = new ArrayList<>();
		list.addAll(5, null); // throw IndexOutOfBoundsException or NullPointerException
	}

}
