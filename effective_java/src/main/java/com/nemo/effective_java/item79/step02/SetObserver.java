package com.nemo.effective_java.item79.step02;

@FunctionalInterface
public interface SetObserver<E> {
	void added(ObservableSet<E> set, E element);
}
