package com.nemo.effective_java.item62.step04;

public class ThreadLocalClient {
	public static void main(String[] args) {
		ThreadLocal<String> threadLocal = new ThreadLocal<>();
		threadLocal.set("value1");
		System.out.println(threadLocal.get()); // value1

		ThreadLocal<String> threadLocal2 = new ThreadLocal<>();
		threadLocal2.set("value2");
		System.out.println(threadLocal2.get()); // value2
	}
}
