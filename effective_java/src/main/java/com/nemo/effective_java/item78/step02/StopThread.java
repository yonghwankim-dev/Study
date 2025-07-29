package com.nemo.effective_java.item78.step02;

public class StopThread {
	private static boolean stopRequested;

	private static synchronized void requestStop() {
		stopRequested = true;
	}

	private static synchronized boolean stopRequested() {
		return stopRequested;
	}

	public static void main(String[] args) throws InterruptedException {
		Thread backgroundThread = new Thread(() -> {
			int i = 0;
			while (!stopRequested()) {
				i++;
			}
		});
		backgroundThread.start();
		Thread.sleep(1000);
		requestStop();
	}
}
