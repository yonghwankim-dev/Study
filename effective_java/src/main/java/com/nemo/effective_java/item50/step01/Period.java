package com.nemo.effective_java.item50.step01;

import java.util.Date;

public final class Period {
	private final Date start;
	private final Date end;

	/**
	 * @param start the beginning of the period
	 * @param end the end of the period; must not be before start
	 * @throws NullPointerException if start or end is null
	 * @throws IllegalArgumentException if start is after end
	 */
	public Period(Date start, Date end) {
		if (start == null || end == null) {
			throw new NullPointerException("Start and end dates must not be null");
		}
		if (start.compareTo(end) > 0) {
			throw new IllegalArgumentException(start + " after " + end);
		}
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return start + " - " + end;
	}
}
