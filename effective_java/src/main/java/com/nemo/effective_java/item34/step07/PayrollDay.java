package com.nemo.effective_java.item34.step07;

public enum PayrollDay {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
	SATURDAY, SUNDAY;

	private static final int MINS_PER_SHIFT = 8 * 60;

	int pay(int minutesWorked, int payRate) {
		int basePay = (minutesWorked / 60) * payRate;
		int overtimePay = switch (this) {
			case SATURDAY, SUNDAY -> basePay / 2;
			default -> minutesWorked <= MINS_PER_SHIFT ? 0 : ((minutesWorked - MINS_PER_SHIFT) / 60) * payRate / 2;
		};

		return basePay + overtimePay;
	}
}
