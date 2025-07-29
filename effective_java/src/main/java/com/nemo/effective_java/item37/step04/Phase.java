package com.nemo.effective_java.item37.step04;

import java.util.EnumMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Phase {
	SOLID, LIQUID, GAS, PLASMA;

	public enum Transition {
		MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID), BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID), SUBLIME(SOLID,
			GAS), DEPOSIT(GAS, SOLID),
		IONIZE(GAS, PLASMA), DEIONIZE(PLASMA, GAS);

		private final Phase from;
		private final Phase to;

		Transition(Phase from, Phase to) {
			this.from = from;
			this.to = to;
		}

		// initialize TransitionMap
		private static final EnumMap<Phase, EnumMap<Phase, Transition>> m = Stream.of(values())
			.collect(Collectors.groupingBy(t -> t.from,
				() -> new EnumMap<>(Phase.class),
				Collectors.toMap(t -> t.to, t -> t, (x, y) -> y, () -> new EnumMap<>(Phase.class))));

		public static Transition from(Phase from, Phase to) {
			return m.get(from).get(to);
		}
	}
}
