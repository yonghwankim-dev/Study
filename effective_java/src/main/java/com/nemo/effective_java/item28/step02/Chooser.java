package com.nemo.effective_java.item28.step02;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chooser {

	private final Object[] choiceArray;

	public Chooser(Collection choices){
		choiceArray = choices.toArray();
	}

	public Object choose(){
		Random random = ThreadLocalRandom.current();
		return choiceArray[random.nextInt(choiceArray.length)];
	}
}
