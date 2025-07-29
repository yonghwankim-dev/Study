package com.nemo.effective_java.item62.step03;

public class ThreadLocalClient {
	public static void main(String[] args) {
		ThreadLocal threadLocal = new ThreadLocal();
		threadLocal.set("value1");
		System.out.println(threadLocal.get()); // value1

		ThreadLocal threadLocal2 = new ThreadLocal();
		threadLocal2.set("value2");
		System.out.println(threadLocal2.get()); // value2
	}
}
