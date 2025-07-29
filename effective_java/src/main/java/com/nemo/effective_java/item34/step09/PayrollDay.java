package com.nemo.effective_java.item34.step09;

public enum PayrollDay {
	MONDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return overtimePayForWeekday(minutesWorked, payRate);
		}
	},
	TUESDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return overtimePayForWeekday(minutesWorked, payRate);
		}
	},
	WEDNESDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return overtimePayForWeekday(minutesWorked, payRate);
		}
	},
	THURSDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return overtimePayForWeekday(minutesWorked, payRate);
		}
	},
	FRIDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return overtimePayForWeekday(minutesWorked, payRate);
		}
	},
	SATURDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return overtimePayForWeekend(minutesWorked, payRate);
		}
	},
	SUNDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return overtimePayForWeekend(minutesWorked, payRate);
		}
	},
	VACATION {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return overtimePayForWeekend(minutesWorked, payRate);
		}
	};

	private static final int MINS_PER_SHIFT = 8 * 60;

	int pay(int minutesWorked, int payRate) {
		int basePay = (minutesWorked / 60) * payRate;
		int overtimePay = overtimePay(minutesWorked, payRate);
		return basePay + overtimePay;
	}

	abstract int overtimePay(int minutesWorked, int payRate);

	int overtimePayForWeekend(int minutesWorked, int payRate) {
		int basePay = (minutesWorked / 60) * payRate;
		return basePay / 2;
	}

	int overtimePayForWeekday(int minutesWorked, int payRate) {
		return minutesWorked <= MINS_PER_SHIFT ? 0 : ((minutesWorked - MINS_PER_SHIFT) / 60) * payRate / 2;
	}
}
