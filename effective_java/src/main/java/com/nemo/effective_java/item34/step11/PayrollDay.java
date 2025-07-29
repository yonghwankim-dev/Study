package com.nemo.effective_java.item34.step11;

import static com.nemo.effective_java.item34.step11.PayrollDay.PayType.*;

public enum PayrollDay {
	MONDAY(WEEKDAY), TUESDAY(WEEKDAY), WEDNESDAY(WEEKDAY),
	THURSDAY(WEEKDAY), FRIDAY(WEEKDAY), SATURDAY(WEEKEND), SUNDAY(WEEKEND);

	private final PayType payType;

	PayrollDay(PayType payType) {
		this.payType = payType;
	}

	int pay(int minutesWorked, int payRate) {
		return payType.pay(minutesWorked, payRate);
	}

	enum PayType {
		WEEKDAY {
			@Override
			int overtimePay(int minutesWorked, int payRate) {
				return minutesWorked <= MINS_PER_SHIFT ? 0 : ((minutesWorked - MINS_PER_SHIFT) / 60) * payRate / 2;
			}
		}, WEEKEND {
			@Override
			int overtimePay(int minutesWorked, int payRate) {
				return (minutesWorked / 60) * payRate / 2;
			}
		};

		private static final int MINS_PER_SHIFT = 8 * 60;

		public int pay(int minutesWorked, int payRate) {
			int basePay = (minutesWorked / 60) * payRate;
			return basePay + overtimePay(minutesWorked, payRate);
		}

		abstract int overtimePay(int minutesWorked, int payRate);
	}
}
