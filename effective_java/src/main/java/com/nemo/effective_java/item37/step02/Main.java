package com.nemo.effective_java.item37.step02;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) {
		Set<Plant> garden = Set.of(
			new Plant("a", Plant.LifeCycle.ANNUAL),
			new Plant("b", Plant.LifeCycle.ANNUAL),
			new Plant("c", Plant.LifeCycle.PERENNIAL),
			new Plant("d", Plant.LifeCycle.BIENNIAL)
		);
		Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);
		for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
			plantsByLifeCycle.put(lc, new HashSet<>());
		}
		for (Plant p : garden) {
			plantsByLifeCycle.get(p.getLifeCycle()).add(p);
		}
		System.out.println(plantsByLifeCycle);

		Map<Plant.LifeCycle, List<Plant>> result = garden.stream()
			.collect(Collectors.groupingBy(Plant::getLifeCycle));
		System.out.println(result);

		EnumMap<Plant.LifeCycle, Set<Plant>> result2 = garden.stream()
			.collect(Collectors.groupingBy(Plant::getLifeCycle, () -> new EnumMap<>(Plant.LifeCycle.class),
				Collectors.toUnmodifiableSet()));
		System.out.println(result2);
	}
}
