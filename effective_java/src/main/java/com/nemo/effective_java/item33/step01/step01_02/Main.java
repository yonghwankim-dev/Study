package com.nemo.effective_java.item33.step01.step01_02;

import java.util.HashSet;

public class Main {
	public static void main(String[] args) {
		Favorites f = new Favorites();

		f.putFavorite(HashSet.class, new HashSet<String>());
		// broken the type safe due to raw type
		HashSet hashSet = f.getFavorite(HashSet.class);
		
		hashSet.add("java");
		System.out.println(hashSet);
	}
}
