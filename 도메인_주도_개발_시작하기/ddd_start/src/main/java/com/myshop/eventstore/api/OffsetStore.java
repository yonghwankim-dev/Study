package com.myshop.eventstore.api;

public interface OffsetStore {
	long get();

	void update(long nextOffset);
}
