package com.nemo.effective_java.item33.step01.step01_91;

public class Main {
	public static void main(String[] args) {
		// Favorites Instance Type is safe, so Favorites is type safe heterogeneous container pattern)
		Favorites f = new Favorites();

		f.putFavorite(String.class, "Java");
		f.putFavorite(Integer.class, 0xcafebabe);
		f.putFavorite(Class.class, Favorites.class);

		String favoriteString = f.getFavorite(String.class);
		Integer favoriteInteger = f.getFavorite(Integer.class);
		Class<?> favoriteClass = f.getFavorite(Class.class);
		/*
			Java
			0xcafebabe
			class com.nemo.effective_java.item33.step01.step01_91.Favorites
		 */
		System.out.printf("%s %x %s%n", favoriteString, favoriteInteger, favoriteClass.getName());
	}
}
