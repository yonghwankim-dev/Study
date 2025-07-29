package com.nemo.effective_java.item39.step03;

public class TestClient {
	public static void main(String[] args) {
		TestAnnotationProcessor.processTests(MyTests.class);
	}
}
