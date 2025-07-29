package com.nemo.effective_java.item28.step03;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		Object choose = new Chooser<>(List.of(1,2,3,4,5)).choose();
		System.out.println(choose);
	}
}
