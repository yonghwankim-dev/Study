package com.nemo.effective_java.item80.step07;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceClient {
	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		executor.schedule(() -> System.out.println("Task1 executed after 3 seconds"), 3,
			TimeUnit.SECONDS);

		executor.scheduleAtFixedRate(
			() -> System.out.println("Task2 executed at fixed interval of 5 seconds"),
			2,
			5,
			TimeUnit.SECONDS);

		executor.scheduleWithFixedDelay(
			() -> System.out.println("Task 3 executed with 3 seconds delay after previous run"), 4, 3,
			TimeUnit.SECONDS);

		try {
			if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
				System.out.println("Executor did not terminate in the specified time.");
			}
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
			executor.shutdownNow();
		} finally {
			executor.shutdown();
		}
		System.out.println("ScheduledExecutorService has been shut down.");
	}
}
