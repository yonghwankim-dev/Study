package com.nemo.effective_java.item80.step08;

import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Long> {
	// task splitting criteria
	private static final int THRESHOLD = 100_000;
	private final long[] numbers;
	private final int start;
	private final int end;

	public SumTask(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		int length = end - start;

		if (length <= THRESHOLD) {
			return computeDirectly();
		}

		// set middle
		int middle = start + length / 2;

		SumTask leftTask = new SumTask(numbers, start, middle);
		SumTask rightTask = new SumTask(numbers, middle, end);

		// Fork the left task asynchronously (run in the background)
		leftTask.fork();

		// Run the right task synchronously (handle with threads assigned)
		Long rightResult = rightTask.compute();

		// Waiting for the results of the left-hand task
		Long leftResult = leftTask.join();

		// return sum of the left and right result
		return leftResult + rightResult;
	}

	private Long computeDirectly() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}
}
