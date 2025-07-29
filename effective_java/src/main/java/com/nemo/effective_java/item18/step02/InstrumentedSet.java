package com.nemo.effective_java.item18.step02;

import java.util.Collection;
import java.util.Set;

class InstrumentedSet<E> extends ForwardingSet<E> {

	private int addCount;

	public InstrumentedSet(Set<E> s) {
		super(s);
		this.addCount = 0;
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
