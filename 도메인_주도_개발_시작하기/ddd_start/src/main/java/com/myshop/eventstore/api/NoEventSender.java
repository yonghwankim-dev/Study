package com.myshop.eventstore.api;

import org.springframework.stereotype.Component;

@Component
public class NoEventSender implements EventSender {
	@Override
	public void send(EventEntry event) {
		System.out.println("Event sending is disabled. Event: " + event);
	}
}
