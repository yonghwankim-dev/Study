package com.nemo.effective_java.item38.step01;

public enum Extendedoperation implements Operation {
	EXP("^") {
		public double apply(double x, double y) {
			return Math.pow(x, y);
		}
	},
	REMAINDER("%") {
		public double apply(double x, double y) {
			return x % y;
		}
	};

	private final String symbol;

	Extendedoperation(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return symbol;
	}
}
