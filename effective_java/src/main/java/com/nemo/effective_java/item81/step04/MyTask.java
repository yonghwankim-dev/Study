package com.nemo.effective_java.item81.step04;

public class MyTask implements Runnable {
	private final String name;

	private final long duration;

	private final TaskCompletionNotifier notifier;

	public MyTask(String name, long duration, TaskCompletionNotifier notifier) {
		this.name = name;
		this.duration = duration;
		this.notifier = notifier;
	}

	@Override
	public void run() {
		try {
			System.out.println(name + " is starting.");
			Thread.sleep(duration); // 작업 시뮬레이션
			System.out.println(name + " is completed.");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			notifier.taskCompleted(); // 작업 완료 알림
		}
	}
}
