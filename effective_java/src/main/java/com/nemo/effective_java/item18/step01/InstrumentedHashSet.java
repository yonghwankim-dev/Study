package com.nemo.effective_java.item18.step01;

import java.util.Collection;
import java.util.HashSet;

class InstrumentedHashSet<E> extends HashSet<E> {
	private int addCount;

	public InstrumentedHashSet() {
		this.addCount = 0;
	}

	public InstrumentedHashSet(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	@Override
	public boolean add(E e) {
		addCount++;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		addCount += c.size();
		return super.addAll(c);
	}

	public int getAddCount() {
		return addCount;
	}
}
