package com.nemo.effective_java.item80.step01;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(() -> System.out.println("Hello"));
		executor.submit(() -> System.out.println("world")).get();
		executor.shutdown();
	}
}
