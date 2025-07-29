package com.nemo.effective_java.item56.step08;

/**
 * The type Floating point math.
 */
public class FloatingPointMath {
	/**
	 * Performs a division of two floating-point numbers.
	 * This method complies with the {@index IEEE 754} standard.
	 *
	 * @param numerator the dividend of the division
	 * @param denominator the divisor of the division
	 * @return the result of the division
	 * @throws ArithmeticException if the denominator is zero
	 */
	public double divide(double numerator, double denominator) {
		if (denominator == 0) {
			throw new ArithmeticException("Division by zero is not allowed.");
		}
		return numerator / denominator;
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		double divide = new FloatingPointMath().divide(1, 0);
		System.out.println(divide);
	}
}
