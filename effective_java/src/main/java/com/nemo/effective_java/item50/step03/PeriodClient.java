package com.nemo.effective_java.item50.step03;

import java.util.Date;

public class PeriodClient {
	public static void main(String[] args) {
		Date start = new Date();
		Date end = new Date();
		Period period = new Period(start, end);
		period.getEnd().setYear(78);
		System.out.println(period); // Fri Sep 27 16:47:59 KST 2024 - Fri Sep 27 16:47:59 KST 2024
	}
}
