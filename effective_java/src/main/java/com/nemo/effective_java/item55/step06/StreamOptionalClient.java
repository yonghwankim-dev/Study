package com.nemo.effective_java.item55.step06;

import java.util.Optional;
import java.util.stream.Stream;

public class StreamOptionalClient {
	public static void main(String[] args) {
		Stream<Optional<String>> stream = createStreamOfOptional();

		stream.filter(Optional::isPresent)
			.map(Optional::get)
			.forEach(System.out::print);
		System.out.println();

		createStreamOfOptional().flatMap(Optional::stream)
			.forEach(System.out::print);
	}

	private static Stream<Optional<String>> createStreamOfOptional() {
		return Stream.of(
			Optional.of("a"),
			Optional.of("b"),
			Optional.empty(),
			Optional.of("c")
		);
	}
}
