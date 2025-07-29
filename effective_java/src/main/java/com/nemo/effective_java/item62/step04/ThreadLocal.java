package com.nemo.effective_java.item62.step04;

import java.util.HashMap;
import java.util.Map;

public final class ThreadLocal<T> {

	private final Map<ThreadLocal<T>, Map<ThreadLocal<T>, T>> threadLocalMap;

	public ThreadLocal() {
		threadLocalMap = new HashMap<>();
	}

	public synchronized void set(T value) {
		threadLocalMap.putIfAbsent(this, new HashMap<>());
		threadLocalMap.get(this).put(this, value);
	}

	public synchronized T get() {
		Map<ThreadLocal<T>, T> map = threadLocalMap.get(this);
		if (map == null) {
			return null;
		}
		return map.get(this);
	}
}
