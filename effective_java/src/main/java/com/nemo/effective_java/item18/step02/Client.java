package com.nemo.effective_java.item18.step02;

import java.time.Instant;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Client {
	public static void main(String[] args) {
		Set<Instant> times = new InstrumentedSet<>(
			new TreeSet<>(Comparator.comparingLong(Instant::toEpochMilli)));
		times.add(Instant.now());
		System.out.println(((InstrumentedSet)times).getAddCount()); // 1
	}
}
