package com.nemo.effective_java.item30.step04;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Main {

	public static <E extends Comparable<E>> Optional<E> max(Collection<E> collection) {
		if (collection.isEmpty()){
			return Optional.empty();
		}
		E result = null;
		for (E e : collection) {
			if (result == null || e.compareTo(result) > 0) {
				result = e;
			}
		}
		return Optional.of(result);
	}

	public static void main(String[] args) {
		System.out.println(max(List.of(1, 2, 3, 4, 5)).orElse(-1)); // 5
	}
}
