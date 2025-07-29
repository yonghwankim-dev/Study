package com.nemo.effective_java.item27.step01;

import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Set<String>	set = new HashSet(); // unchecked conversion
		Set<String> set2 = new HashSet<>(); // solution

		set.add("kim");
		set2.add("lee");
		System.out.println(set);
		System.out.println(set2);
	}
}
