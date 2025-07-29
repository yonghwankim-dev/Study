package com.nemo.effective_java.item69.step02;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class SharedStateExample {

	// AtomicReference를 사용하여 스레드 안전하게 상태를 관리
	private AtomicReference<String> state = new AtomicReference<>("Initial State");

	/**
	 * 외부 동기화 없이 여러 스레드가 동시에 상태를 조회할 수 있습니다.
	 * 상태가 null일 경우를 Optional로 안전하게 처리합니다.
	 *
	 * @return 상태가 존재하면 그 값을 반환하고, 없으면 "Default Value" 반환
	 */
	public String getState() {
		return Optional.ofNullable(state.get()).orElse("Default Value");
	}

	/**
	 * 상태를 업데이트합니다.
	 * 여러 스레드가 동시에 접근할 수 있지만 AtomicReference로 스레드 안전합니다.
	 *
	 * @param newState 새로운 상태 값
	 */
	public void setState(String newState) {
		state.set(newState);
	}

	public static void main(String[] args) {
		SharedStateExample example = new SharedStateExample();

		// 여러 스레드가 동시에 상태를 변경하고 조회하는 예제
		Runnable task = () -> {
			example.setState("Updated by " + Thread.currentThread().getName());
			System.out.println(Thread.currentThread().getName() + ": " + example.getState());
		};

		// 3개의 스레드를 실행하여 상태를 동시에 업데이트하고 조회
		Thread thread1 = new Thread(task, "Thread 1");
		Thread thread2 = new Thread(task, "Thread 2");
		Thread thread3 = new Thread(task, "Thread 3");

		thread1.start();
		thread2.start();
		thread3.start();
	}
}
