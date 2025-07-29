package com.nemo.effective_java.item75;

public class MyIntArrayClient {
	public static void main(String[] args) {
		MyIntArray array = new MyIntArray(10);
		try {
			int result = array.get(10);
			System.out.println(result);
		} catch (MyIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}
