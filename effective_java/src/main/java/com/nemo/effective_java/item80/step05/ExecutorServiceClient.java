package com.nemo.effective_java.item80.step05;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceClient {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
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

		tasks.forEach(executor::submit);

		executor.shutdown();

		try {
			if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
				System.out.println("Not all task finished within the timeout. Forcing shutdown...");
				executor.shutdownNow();
			} else {
				System.out.println("All tasks completed successfully.");
			}
		} catch (InterruptedException e) {
			System.out.println("awaitTermination interrupted");
			executor.shutdownNow();
		} finally {
			executor.shutdown();
		}
	}
}
