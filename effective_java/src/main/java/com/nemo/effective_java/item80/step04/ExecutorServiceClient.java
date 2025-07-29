package com.nemo.effective_java.item80.step04;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceClient {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		List<Callable<String>> tasks = Arrays.asList(
			() -> {
				TimeUnit.SECONDS.sleep(2);
				return "Task 1 Completed";
			},
			() -> {
				TimeUnit.SECONDS.sleep(1);
				return "Task 2 Completed";
			},
			() -> {
				TimeUnit.SECONDS.sleep(3);
				return "Task 3 Completed";
			}
		);

		try {
			List<Future<String>> futures = executor.invokeAll(tasks);
			futures.forEach(future -> {
				try {
					String result = future.get();
					System.out.println("Completed task result : " + result);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			});
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			executor.shutdown();
		}
	}
}
