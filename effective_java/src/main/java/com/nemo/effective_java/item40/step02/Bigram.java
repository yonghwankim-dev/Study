package com.nemo.effective_java.item40.step02;

public class Bigram {
	private final char first;
	private final char second;

	public Bigram(char first, char second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Bigram b)) {
			return false;
		}
		return b.first == first && b.second == second;
	}

	@Override
	public int hashCode() {
		return 31 * first + second;
	}
}
