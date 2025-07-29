package com.nemo.effective_java.item28.step04;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chooser<T> {
	private final List<T> choiceList;

	public Chooser(Collection<T> choiceList) {
		this.choiceList = new ArrayList<>(choiceList);
	}

	public T choose() {
		Random random = ThreadLocalRandom.current();
		return choiceList.get(random.nextInt(choiceList.size()));
	}
}
