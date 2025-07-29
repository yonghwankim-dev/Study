package com.nemo.effective_java.item39.step02;

public class TestClient {
	public static void main(String[] args) {
		TestAnnotationProcessor.processTests(MyTests.class);
	}
}
