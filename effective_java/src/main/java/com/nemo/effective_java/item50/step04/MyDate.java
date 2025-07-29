package com.nemo.effective_java.item50.step04;

import java.util.Date;

public class MyDate extends Date {
	private String additionalInfo;

	public MyDate() {
		super();
		this.additionalInfo = "Extra Info";
	}

	@Override
	public Object clone() {
		// Date의 clone 메서드를 호출하지 않고 다른 방식으로 클론을 정의할 수 있음
		MyDate cloned = (MyDate)super.clone();
		cloned.additionalInfo = "Cloned Extra Info";
		return cloned;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public static void main(String[] args) {
		MyDate original = new MyDate();
		MyDate cloned = (MyDate)original.clone();

		System.out.println("Original Info: " + original.getAdditionalInfo());
		System.out.println("Cloned Info: " + cloned.getAdditionalInfo());
	}
}
