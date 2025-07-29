package com.nemo.effective_java.item81.step04;

public class TaskCompletionNotifier {
	private final int totalTasks;
	private int completedTasks = 0;

	public TaskCompletionNotifier(int totalTasks) {
		this.totalTasks = totalTasks;
	}

	public synchronized void taskCompleted() {
		completedTasks++;
		if (completedTasks == totalTasks) {
			notifyAll(); // notify if totalTasks is Completed
		}
	}

	public synchronized void awaitCompletion() throws InterruptedException {
		while (completedTasks < totalTasks) {
			wait();
		}
	}
}
