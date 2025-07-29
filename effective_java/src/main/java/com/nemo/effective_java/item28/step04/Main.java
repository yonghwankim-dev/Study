package com.nemo.effective_java.item28.step04;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		Chooser<Integer> chooser = new Chooser<>(List.of(1, 2, 3, 4, 5));
		System.out.println(chooser.choose());
	}
}
