package com.nemo.effective_java.item33.step01.step01_91;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Favorites {
	private final Map<Class<?>, Object> store = new HashMap<>();

	public <T> void putFavorite(Class<T> type, T instance) {
		store.put(Objects.requireNonNull(type), instance);
	}
	
	public <T> T getFavorite(Class<T> type) {
		return type.cast(store.get(type));
	}
}
