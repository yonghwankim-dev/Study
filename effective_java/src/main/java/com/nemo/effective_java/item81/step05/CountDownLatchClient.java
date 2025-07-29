package com.nemo.effective_java.item81.step05;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchClient {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		int concurrency = 5;
		Runnable action = () -> {
			try {
				Thread.sleep((long)(Math.random() * 2000) + 1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		};
		long times = time(executor, concurrency, action);
		System.out.println(times + "ms"); // 2002ms

		executor.shutdown();
	}

	public static long time(Executor executor, int concurrency, Runnable action) throws InterruptedException {
		CountDownLatch ready = new CountDownLatch(concurrency);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch done = new CountDownLatch(concurrency);

		for (int i = 0; i < concurrency; i++) {
			executor.execute(() -> {
				ready.countDown();

				try {
					// wait until all threads are ready
					start.await();
					action.run();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} finally {
					done.countDown();
				}
			});
		}

		ready.await(); // If five threads call the ready.countDown() method, the ready.await() method releases the standby.
		long startMillis = System.currentTimeMillis();
		start.countDown(); // Calling the start.countDown() method releases the wait for the start.await() method.
		done.await(); // If five threads call do.countDown(), the wait for the do.await() method is released
		return System.currentTimeMillis() - startMillis;
	}
}
