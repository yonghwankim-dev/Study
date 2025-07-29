package com.nemo.effective_java.item28.step03;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chooser<T> {

	private final T[] choiceArray;

	public Chooser(Collection<T> choiceArray) {
		// this.choiceArray = choiceArray; // CompileError
		this.choiceArray = (T[]) choiceArray.toArray(); // casting the Object Array -> T Array
	}

	public Object choose(){
		Random random = ThreadLocalRandom.current();
		return choiceArray[random.nextInt(choiceArray.length)];
	}
}
