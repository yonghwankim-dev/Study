package com.nemo.effective_java.item49.step02;

import java.util.Objects;

public class MyStrategy {
	private final String strategy;

	public MyStrategy(String strategy) {
		this.strategy = Objects.requireNonNull(strategy, "전략");
	}

	@Override
	public String toString() {
		return strategy;
	}
}
