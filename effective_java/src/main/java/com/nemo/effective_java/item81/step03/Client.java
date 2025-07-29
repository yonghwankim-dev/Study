package com.nemo.effective_java.item81.step03;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Client {
	private static final int NUM_THREADS = 10;
	private static final int NUM_OPERATIONS = 1_000_000;

	public static void main(String[] args) {
		Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
		Map<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();

		long synchronizedMapTime = testMap(synchronizedMap);
		long concurrentMapTime = testMap(concurrentMap);
		System.out.println("SynchronizedMap Time: " + synchronizedMapTime + " ms"); // 817ms
		System.out.println("ConcurrentHashMap Time: " + concurrentMapTime + " ms"); // 354ms
		double times = (double)synchronizedMapTime / (double)concurrentMapTime;
		System.out.printf("about %.2f times", times); // 2.31 times
	}

	private static long testMap(Map<Integer, Integer> map) {
		// 시작 시간 측정
		long startTime = System.currentTimeMillis();

		// 스레드 배열
		Thread[] threads = new Thread[NUM_THREADS];

		// NUM_THREADS 만큼의 스레드 생성
		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i] = new Thread(() -> {
				for (int j = 0; j < NUM_OPERATIONS; j++) {
					map.put(j, j); // 데이터 추가
				}
			});
		}

		// 스레드 시작
		for (Thread thread : threads) {
			thread.start();
		}

		// 모든 스레드가 종료될 때까지 대기
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace(System.out);
			}
		}

		// 종료 시간 측정
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
}
