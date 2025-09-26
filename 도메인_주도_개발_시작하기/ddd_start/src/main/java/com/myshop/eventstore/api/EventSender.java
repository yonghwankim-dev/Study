package com.myshop.eventstore.api;

public interface EventSender {
	void send(EventEntry event);
}
