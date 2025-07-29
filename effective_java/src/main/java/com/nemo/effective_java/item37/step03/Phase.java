package com.nemo.effective_java.item37.step03;

public enum Phase {
	SOLID, LIQUID, GAS;

	public enum Transition {
		MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;
		private static final Transition[][] TRANSITIONS = {
			{null, MELT, SUBLIME},
			{FREEZE, null, BOIL},
			{DEPOSIT, CONDENSE, null}
		};

		public static Transition from(Phase src, Phase dst) {
			return TRANSITIONS[src.ordinal()][dst.ordinal()];
		}
	}
}
