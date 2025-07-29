package com.nemo.effective_java.item62.step03;

import java.util.HashMap;
import java.util.Map;

public final class ThreadLocal {

	private final Map<ThreadLocal, Map<ThreadLocal, Object>> threadLocalMap;

	public ThreadLocal() {
		threadLocalMap = new HashMap<>();
	}

	public synchronized void set(Object value) {
		threadLocalMap.putIfAbsent(this, new HashMap<>());
		threadLocalMap.get(this).put(this, value);
	}

	public synchronized Object get() {
		Map<ThreadLocal, Object> map = threadLocalMap.get(this);
		if (map == null) {
			return null;
		}
		return map.get(this);
	}
}
