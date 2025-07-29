package com.nemo.effective_java.item39.step04;

public class TestClient {
	public static void main(String[] args) {
		TestAnnotationProcessor.processTests(MyTests.class);
	}
}
