package com.nemo.effective_java.item50.step01;

import java.util.Date;

public class PeriodClient {
	public static void main(String[] args) {
		Date start = new Date();
		Date end = new Date();
		Period period = new Period(start, end);
		end.setYear(78);
		System.out.println(period); // Fri Sep 27 16:48:08 KST 2024 - Wed Sep 27 16:48:08 KST 1978
	}
}
