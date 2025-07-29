package com.nemo.effective_java.item55.step04;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class WordClient {
	public static void main(String[] args) {
		List<String> words = Collections.emptyList();
		// optional utilization 1 : available set the default value
		String lastWordInLexicon = max(words).orElse("no word");
		System.out.println(lastWordInLexicon);

		// optional utilization 2 : available throw the exception
		try {
			lastWordInLexicon = max(words).orElseThrow(NoSuchElementException::new);
			System.out.println(lastWordInLexicon);
		} catch (NoSuchElementException e) {
			System.out.println("throw the exception");
		}

		// optional utilization 3 : It is assumed that the value is always filled
		// Nevertheless, if the collection is empty, the NoSuchElementException exception occurs
		try {
			lastWordInLexicon = max(words).get();
		} catch (NoSuchElementException e) {
			System.out.println("throw the exception");
		}
	}

	public static <E extends Comparable<E>> Optional<E> max(Collection<E> collection) {
		return collection.stream()
			.max(Comparator.naturalOrder());
	}
}
