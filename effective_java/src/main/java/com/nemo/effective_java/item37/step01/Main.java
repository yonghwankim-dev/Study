package com.nemo.effective_java.item37.step01;

import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Set<Plant>[] plantsByLifeCycle = (Set<Plant>[])new Set[Plant.LifeCycle.values().length];
		for (int i = 0; i < plantsByLifeCycle.length; i++) {
			plantsByLifeCycle[i] = new HashSet<>();
		}

		Set<Plant> garden = Set.of(
			new Plant("a", Plant.LifeCycle.ANNUAL),
			new Plant("b", Plant.LifeCycle.ANNUAL),
			new Plant("c", Plant.LifeCycle.PERENNIAL),
			new Plant("d", Plant.LifeCycle.BIENNIAL)
		);
		for (Plant p : garden) {
			plantsByLifeCycle[p.getLifeCycle().ordinal()].add(p);
		}

		// Print the results
		for (int i = 0; i < plantsByLifeCycle.length; i++) {
			System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
		}
	}
}
