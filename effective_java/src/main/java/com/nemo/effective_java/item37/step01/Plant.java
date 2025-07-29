package com.nemo.effective_java.item37.step01;

public class Plant {
	enum LifeCycle {ANNUAL, PERENNIAL, BIENNIAL}

	private final String name;
	private final LifeCycle lifeCycle;

	public Plant(String name, LifeCycle lifeCycle) {
		this.name = name;
		this.lifeCycle = lifeCycle;
	}

	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public String toString() {
		return name;
	}
}
