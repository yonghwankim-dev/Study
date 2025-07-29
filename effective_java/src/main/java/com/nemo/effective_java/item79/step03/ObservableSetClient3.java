package com.nemo.effective_java.item79.step03;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObservableSetClient3 {
	public static void main(String[] args) {
		ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

		set.addObserver(new SetObserver<>() {
			@Override
			public void added(ObservableSet<Integer> s, Integer element) {
				System.out.println(element);
				if (element == 23) {
					ExecutorService exec = Executors.newSingleThreadExecutor();
					try {
						// deadlock
						exec.submit(() -> s.removeObserver(this)).get();
					} catch (ExecutionException | InterruptedException e) {
						throw new AssertionError(e);
					} finally {
						exec.shutdown();
					}
				}
			}
		});

		try {
			for (int i = 0; i < 100; i++) {
				set.add(i);
			}
		} catch (ConcurrentModificationException e) {
			e.printStackTrace(System.err);
		}
		System.out.println(set);
	}
}
