package com.nemo.effective_java.item06.step00;

public class StringClient {
	public static void main(String[] args) {
		String bikini = new String("bikini");
		System.out.println(bikini.hashCode());      // -1389048736
		System.out.println("bikini".hashCode());    // -1389048736
		System.out.println(bikini == "bikini");    // false
	}
}
