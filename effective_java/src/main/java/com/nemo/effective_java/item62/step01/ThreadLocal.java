package com.nemo.effective_java.item62.step01;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocal {

	private static final Map<String, Map<String, Object>> threadLocalMap = new HashMap<>();

	private ThreadLocal() {

	}

	public static synchronized void set(String key, Object value) {
		threadLocalMap.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
		threadLocalMap.get(key).put(key, value);
	}

	public static synchronized Object get(String key) {
		Map<String, Object> map = threadLocalMap.get(key);
		if (map == null) {
			return null;
		}
		return map.get(key);
	}
}
