package com.nemo.effective_java.item62.step01;

public class ThreadLocalClient {
	public static void main(String[] args) {
		ThreadLocal.set("key1", "value1");
		ThreadLocal.set("key2", "value2");

		System.out.println(ThreadLocal.get("key1")); // value1
		System.out.println(ThreadLocal.get("key2")); // value2
	}
}
