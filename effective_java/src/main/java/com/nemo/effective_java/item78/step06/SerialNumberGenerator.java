package com.nemo.effective_java.item78.step06;

import java.util.concurrent.atomic.AtomicLong;

public class SerialNumberGenerator {
	private static final AtomicLong nextSerialNumber = new AtomicLong();

	public static long generateSerialNumber() {
		return nextSerialNumber.getAndIncrement();
	}

	public static void main(String[] args) throws InterruptedException {
		Runnable task = () -> {
			for (int i = 0; i < 1_000_000; i++) {
				System.out.println(Thread.currentThread().getName() + " : " + generateSerialNumber());
			}
		};

		Thread t1 = new Thread(task, "Thread-1");
		Thread t2 = new Thread(task, "Thread-2");
		Thread t3 = new Thread(task, "Thread-3");

		t1.start();
		t2.start();
		t3.start();

		t1.join();
		t2.join();
		t3.join();

		System.out.println("Final nextSerialNumber: " + nextSerialNumber);
	}
}
