package com.myshop.eventstore.ui;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.eventstore.api.EventEntry;
import com.myshop.eventstore.api.EventStore;

@RestController
public class EventApi {
	private final EventStore eventStore;

	public EventApi(EventStore eventStore) {
		this.eventStore = eventStore;
	}

	@GetMapping("/api/events")
	public List<EventEntry> list(
		@RequestParam("offset") Long offset,
		@RequestParam("limit") Long limit
	) {
		return eventStore.get(offset, limit);
	}
}
