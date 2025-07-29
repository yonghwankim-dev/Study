package com.nemo.effective_java.item49.step02;

public class MyStrategyClient {
	public static void main(String[] args) {
		MyStrategy myStrategy = new MyStrategy(null);
		System.out.println(myStrategy);
	}
}
