package com.nemo.effective_java.item62.step02;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocal {

	private static final Map<Key, Map<Key, Object>> threadLocalMap = new HashMap<>();

	private ThreadLocal() {
		throw new UnsupportedOperationException("ThreadLocal is not instantiable");
	}

	public static synchronized void set(Key key, Object value) {
		threadLocalMap.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
		threadLocalMap.get(key).put(key, value);
	}

	public static synchronized Object get(Key key) {
		Map<Key, Object> map = threadLocalMap.get(key);
		if (map == null) {
			return null;
		}
		return map.get(key);
	}

	public static Key getKey() {
		return new Key();
	}

	public static class Key {
		Key() {

		}
	}
}
