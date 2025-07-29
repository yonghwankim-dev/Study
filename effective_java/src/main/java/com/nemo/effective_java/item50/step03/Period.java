package com.nemo.effective_java.item50.step03;

import java.util.Date;
import java.util.Objects;

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
		this.start = new Date(Objects.requireNonNull(start, "start must not null").getTime());
		this.end = new Date(Objects.requireNonNull(end, "end must not null").getTime());
		if (this.start.compareTo(this.end) > 0) {
			throw new IllegalArgumentException(start + " after " + end);
		}
	}

	public Date getStart() {
		return new Date(start.getTime());
	}

	public Date getEnd() {
		return new Date(end.getTime());
	}

	@Override
	public String toString() {
		return start + " - " + end;
	}
}
