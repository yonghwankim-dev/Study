package com.nemo.effective_java.item80.step02;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceClient {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			executor.submit(() -> {
				try {
					Thread.sleep(1000);
					System.out.println("hello");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		} finally {
			executor.shutdown();
		}
		System.out.println("world");
	}
}
