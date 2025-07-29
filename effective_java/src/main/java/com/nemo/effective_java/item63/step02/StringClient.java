package com.nemo.effective_java.item63.step02;

public class StringClient {
	public static void main(String[] args) {
		StringClient stringClient = new StringClient();
		int numItems = 1_000_000;
		String result = stringClient.statement(numItems);
		System.out.println(result);
	}

	public String statement(int numItems) {
		int lineWidth = 80;
		StringBuilder result = new StringBuilder(numItems * lineWidth);
		for (int i = 0; i < numItems; i++) {
			result.append("item").append(i);
		}
		return result.toString();
	}

}
