package com.nemo.effective_java.item78.step03;

public class StopThread {
	// The volatile modifier is not related to atomic execution, but it guarantees that the most recently written value is always read.
	private static volatile boolean stopRequested;

	/**
	 * BackgroundThread
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread backgroundThread = new Thread(() -> {
			int i = 0;
			while (!stopRequested) {
				i++;
			}
		});
		backgroundThread.start();

		Thread.sleep(1000);
		stopRequested = true;
	}
}
