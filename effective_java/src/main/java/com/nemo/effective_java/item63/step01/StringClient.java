package com.nemo.effective_java.item63.step01;

public class StringClient {
	public static void main(String[] args) {
		StringClient stringClient = new StringClient();
		int numItems = 1_000_000;
		String result = stringClient.statement(numItems);
		System.out.println(result);
	}

	public String statement(int numItems) {
		String result = "";
		for (int i = 0; i < numItems; i++) {
			result += ("item" + i);
		}
		return result;
	}

}
