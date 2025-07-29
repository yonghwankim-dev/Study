package com.nemo.effective_java.item62.step02;

public class ThreadLocalClient {
	public static void main(String[] args) {
		ThreadLocal.Key key1 = ThreadLocal.getKey();
		ThreadLocal.Key key2 = ThreadLocal.getKey();
		ThreadLocal.set(key1, "value1");
		ThreadLocal.set(key2, "value2");

		System.out.println(ThreadLocal.get(key1)); // value1
		System.out.println(ThreadLocal.get(key2)); // value2
	}
}
