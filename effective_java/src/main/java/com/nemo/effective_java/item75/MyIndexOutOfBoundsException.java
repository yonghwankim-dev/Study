package com.nemo.effective_java.item75;

public class MyIndexOutOfBoundsException extends RuntimeException {

	private final int lowerBound;
	private final int upperBound;
	private final int index;

	/**
	 * Instantiates a new My index out of bounds exception.
	 *
	 * @param lowerBound Minimum value of the index
	 * @param upperBound Maximum value of the index
	 * @param index Index to access the elements of the array
	 */
	public MyIndexOutOfBoundsException(int lowerBound, int upperBound, int index) {
		// Generates a detailed message for capturing failures.
		super(String.format("lowerBound:%d, upperBound:%d, index: %d", lowerBound, upperBound, index));

		// Saves failure information for use within the program.
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.index = index;
	}

	@Override
	public String toString() {
		return String.format("lowerBound:%d, upperBound:%d, index: %d", lowerBound, upperBound, index);
	}
}
