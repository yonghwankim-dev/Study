package com.nemo.effective_java.item56.step02;

public class GeometricSeriesCalculator {
	/**
	 * Calculates the sum of an infinite geometric series.
	 * A geometric series converges if {@literal |r| < 1}.
	 * If the series converges, the sum is given by {@literal a / (1 - r)}.
	 *
	 * @param a the first term of the geometric series
	 * @param r the common ratio of the geometric series
	 * @return the sum of the series if it converges
	 * @throws IllegalArgumentException if {@literal |r| >= 1} (series does not converge)
	 */
	public double sumOfGeometricSeries(double a, double r) {
		if (Math.abs(r) >= 1) {
			throw new IllegalArgumentException("The geometric series does not converge for |r| >= 1.");
		}
		return a / (1 - r);
	}

}
