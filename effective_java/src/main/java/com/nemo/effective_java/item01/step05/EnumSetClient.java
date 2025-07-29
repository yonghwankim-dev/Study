package com.nemo.effective_java.item01.step05;

import java.time.DayOfWeek;
import java.util.EnumSet;

public class EnumSetClient {
	public static void main(String[] args) {
		EnumSet<DayOfWeek> dayOfWeeks = EnumSet.allOf(DayOfWeek.class);
		System.out.println(dayOfWeeks);
	}
}
