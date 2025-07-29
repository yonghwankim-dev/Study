package com.nemo.effective_java.item79.step02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ObservableSet<E> extends ForwardingSet<E> {

	private final List<SetObserver<E>> observers = new ArrayList<>();

	public ObservableSet(Set<E> s) {
		super(s);
	}

	public void addObserver(SetObserver<E> observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}

	public void removeObserver(SetObserver<E> observer) {
		synchronized (observers) {
			observers.remove(observer);
		}
	}

	private void notifyElementAdded(E element) {
		List<SetObserver<E>> snapshot;
		synchronized (observers) {
			snapshot = new ArrayList<>(observers);
		}
		for (SetObserver<E> observer : snapshot) {
			observer.added(this, element);
		}
	}

	@Override
	public boolean add(E element) {
		boolean added = super.add(element);
		if (added) {
			notifyElementAdded(element);
		}
		return added;
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean result = false;
		for (E element : collection) {
			result |= add(element);
		}
		return result;
	}

	@Override
	public String toString() {
		return observers.toString();
	}
}
