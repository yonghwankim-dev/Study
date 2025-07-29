package com.nemo.effective_java.item61.step04;

public class SumClient {
	public static void main(String[] args) {
		SumClient sumClient = new SumClient();
		sumClient.sum();
		;
	}

	public void sum() {
		Long sum = 0L;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println(sum);
	}
}
