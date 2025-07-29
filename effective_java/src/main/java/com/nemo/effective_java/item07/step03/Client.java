package com.nemo.effective_java.item07.step03;

import java.util.Map;
import java.util.WeakHashMap;

public class Client {

	static class Key {
		private String name;

		public Key(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Key [name=" + name + "]";
		}
	}

	public static void main(String[] args) {
		Map<Key, String> map = new WeakHashMap<>();
		Key key1 = new Key("key1");
		Key key2 = new Key("key2");

		map.put(key1, "value1");
		map.put(key2, "value2");

		System.out.println("Before GC: " + map);
		key1 = null;

		System.gc();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
		}
		System.out.println("After GC: " + map);
	}
}
