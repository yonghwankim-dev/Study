package com.nemo.effective_java.item80.step06;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceClient {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executor);
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

		tasks.forEach(completionService::submit);

		try {
			for (int i = 0; i < 3; i++) {
				Future<String> future = completionService.take();
				System.out.println("Completed task result : " + future.get());
			}
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		} finally {
			executor.shutdownNow();
		}
	}
}
