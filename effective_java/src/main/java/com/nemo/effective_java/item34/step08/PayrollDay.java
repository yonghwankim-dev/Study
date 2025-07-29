package com.nemo.effective_java.item34.step08;

public enum PayrollDay {
	MONDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return minutesWorked <= MINS_PER_SHIFT ? 0 : ((minutesWorked - MINS_PER_SHIFT) / 60) * payRate / 2;
		}
	},
	TUESDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return minutesWorked <= MINS_PER_SHIFT ? 0 : ((minutesWorked - MINS_PER_SHIFT) / 60) * payRate / 2;
		}
	},
	WEDNESDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return minutesWorked <= MINS_PER_SHIFT ? 0 : ((minutesWorked - MINS_PER_SHIFT) / 60) * payRate / 2;
		}
	},
	THURSDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return minutesWorked <= MINS_PER_SHIFT ? 0 : ((minutesWorked - MINS_PER_SHIFT) / 60) * payRate / 2;
		}
	},
	FRIDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			return minutesWorked <= MINS_PER_SHIFT ? 0 : ((minutesWorked - MINS_PER_SHIFT) / 60) * payRate / 2;
		}
	},
	SATURDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			int basePay = (minutesWorked / 60) * payRate;
			return basePay / 2;
		}
	},
	SUNDAY {
		@Override
		int overtimePay(int minutesWorked, int payRate) {
			int basePay = (minutesWorked / 60) * payRate;
			return basePay / 2;
		}
	},
	VACATION {
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

	abstract int overtimePay(int minutesWorked, int payRate);
}
