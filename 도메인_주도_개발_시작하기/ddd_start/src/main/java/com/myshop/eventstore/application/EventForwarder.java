package com.myshop.eventstore.application;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myshop.eventstore.api.EventEntry;
import com.myshop.eventstore.api.EventSender;
import com.myshop.eventstore.api.EventStore;
import com.myshop.eventstore.api.OffsetStore;

@Component
public class EventForwarder {

	private static final int DEFAULT_LIMIT_SIZE = 100;
	private static final Logger logger = Logger.getLogger(EventForwarder.class.getName());

	private final EventStore eventStore;
	private final OffsetStore offsetStore;
	private final EventSender eventSender;
	private final int limitSize = DEFAULT_LIMIT_SIZE;

	public EventForwarder(EventStore eventStore, OffsetStore offsetStore, EventSender eventSender) {
		this.eventStore = eventStore;
		this.offsetStore = offsetStore;
		this.eventSender = eventSender;
	}

	@Scheduled(initialDelay = 1000L, fixedDelay = 1000L)
	public void getAndSend() {
		Long nextOffset = getNextOffset();
		List<EventEntry> events = eventStore.get(nextOffset, limitSize);
		if (!events.isEmpty()) {
			int processedCount = sendEvent(events);
			if (processedCount > 0) {
				saveNextOffset(nextOffset + processedCount);
			}
		}

	}

	private Long getNextOffset() {
		return offsetStore.get();
	}

	private int sendEvent(List<EventEntry> events) {
		int processedCount = 0;
		try {
			for (EventEntry event : events) {
				eventSender.send(event);
				processedCount++;
			}
		} catch (Exception exception) {
			logger.warning("Failed to send event: " + exception.getMessage());
		}
		return processedCount;
	}

	private void saveNextOffset(long nextOffset) {
		offsetStore.update(nextOffset);
	}
}
