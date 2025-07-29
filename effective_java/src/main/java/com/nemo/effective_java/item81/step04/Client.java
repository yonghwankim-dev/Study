package com.nemo.effective_java.item81.step04;

import java.util.ArrayList;
import java.util.List;

public class Client {
	public static void main(String[] args) {
		int numberOfTasks = 5;
		TaskCompletionNotifier notifier = new TaskCompletionNotifier(numberOfTasks);
		List<Thread> threads = new ArrayList<>();

		// 작업 생성 및 스레드 시작
		long startTime = System.currentTimeMillis();
		for (int i = 1; i <= numberOfTasks; i++) {
			MyTask task = new MyTask("Task " + i, (long)(Math.random() * 2000) + 1000, notifier);
			Thread thread = new Thread(task);
			threads.add(thread);
			thread.start();
		}

		try {
			notifier.awaitCompletion(); // 모든 작업 완료 대기
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		long endTime = System.currentTimeMillis();
		System.out.println("All tasks completed in: " + (endTime - startTime) + " ms"); // 2995ms

		// 모든 스레드가 종료될 때까지 대기
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
