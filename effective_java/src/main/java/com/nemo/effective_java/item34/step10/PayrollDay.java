package com.nemo.effective_java.item34.step10;

public enum PayrollDay {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
	SATURDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			int basePay = (minutesWorked / 60) * payRate;
			return basePay / 2;
		}
	}, SUNDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			int basePay = (minutesWorked / 60) * payRate;
			return basePay / 2;
		}
	}, VACATION {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			int basePay = (minutesWorked / 60) * payRate;
			return basePay / 2;
		}
	};

	private static final int MINS_PER_SHIFT = 8 * 60;

	int pay(int minutesWorked, int payRate) {
		int basePay = (minutesWorked / 60) * payRate;
		int overtimePay = overtimePay(minutesWorked, payRate);
		return basePay + overtimePay;
	}

	int overtimePay(int minutesWorked, int payRate) {
		return minutesWorked <= MINS_PER_SHIFT ? 0 : ((minutesWorked - MINS_PER_SHIFT) / 60) * payRate / 2;
	}
}
