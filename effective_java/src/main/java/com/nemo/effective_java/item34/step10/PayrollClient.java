package com.nemo.effective_java.item34.step10;

public class PayrollClient {
	public static void main(String[] args) {
		// 시간당 기본 임금
		int payRate = 9160;

		// 근무한 분 수 (예: 9시간 근무)
		int minutesWorked = 9 * 60; // 9시간 * 60분 = 540분

		// 각 요일에 대한 급여 계산
		for (PayrollDay day : PayrollDay.values()) {
			int pay = day.pay(minutesWorked, payRate);
			System.out.printf("Day: %s, Worked: %d minutes, Pay: %,d KRW%n",
				day, minutesWorked, pay);
		}
	}
}
