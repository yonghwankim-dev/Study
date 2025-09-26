package com.myshop.eventstore.infrastructure;

import org.springframework.stereotype.Component;

import com.myshop.eventstore.api.OffsetStore;

@Component
public class MemoryOffsetStore implements OffsetStore {

	private long nextOffset;

	public MemoryOffsetStore() {
		nextOffset = 0L;
	}

	@Override
	public long get() {
		return nextOffset;
	}

	@Override
	public void update(long nextOffset) {
		this.nextOffset = nextOffset;
	}
}
