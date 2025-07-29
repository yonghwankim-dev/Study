package com.nemo.effective_java.item78.step01;

public class StopThread {
	private static boolean stopRequested;

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
