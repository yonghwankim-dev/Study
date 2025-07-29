package com.nemo.effective_java.item49.step01;

import java.math.BigInteger;

public class MyBigInteger {
	private final int value;

	public MyBigInteger(int value) {
		this.value = value;
	}

	/**
	 * return (value mod m).
	 * This method differs from the remoaner method in that it always returns a nonnegative BigInteger.
	 *
	 * @param m coefficient (m must positive)
	 * @return (value mod m)
	 * @throws ArithmeticException if m is less than or equal to 0
	 */
	public BigInteger mod(BigInteger m) {
		if (m.signum() <= 0) {
			throw new ArithmeticException("m must positive. " + m);
		}
		return BigInteger.valueOf(value % m.longValue());
	}
}
