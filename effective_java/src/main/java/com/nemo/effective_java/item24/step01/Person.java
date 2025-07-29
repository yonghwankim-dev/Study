package com.nemo.effective_java.item24.step01;

public class Person {
	private static final String HYPEN = "-";

	private final String name;
	private final Phone phone;

	public Person(String name, String phoneNumber) {
		this.name = name;
		this.phone = new Phone(phoneNumber);
	}

	public String getPersonInfo(){
		return name + " " + String.format("%s-%s-%s", phone.areaCode, phone.middleNumber, phone.lineNumber);
	}

	private static class Phone{
		private final String areaCode;
		private final String middleNumber;
		private final String lineNumber;

		public Phone(String number) {
			String[] split = number.split(HYPEN);
			this.areaCode = split[0];
			this.middleNumber = split[1];
			this.lineNumber = split[2];
		}
	}
}
