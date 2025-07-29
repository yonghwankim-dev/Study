package com.nemo.effective_java.item28.step02;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		Object choose = new Chooser(List.of(1, 2, 3, 4, 5, 6)).choose();
		System.out.println(choose);

		System.out.println(new Chooser(List.of("a", "b", "c")).choose());

		Chooser chooser2 = new Chooser(List.of("a", 1, 2, "b"));
		try{
			String choose1 = (String)chooser2.choose(); // String?, Integer? => ClassCastException
			System.out.println(choose1);
		}catch (ClassCastException e) {
			System.out.println(e);
		}
	}
}
