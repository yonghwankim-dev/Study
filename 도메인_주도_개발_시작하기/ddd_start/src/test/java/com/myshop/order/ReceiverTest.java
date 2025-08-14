package com.myshop.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.myshop.order.domain.Receiver;

class ReceiverTest {

	@Test
	void canCreateReceiver() {
		String name = "John Doe";
		String phoneNumber = "123-456-7890";

		Receiver receiver = new Receiver(name, phoneNumber);

		assertNotNull(receiver);
		assertEquals(name, receiver.getName());
		assertEquals(phoneNumber, receiver.getPhoneNumber());
	}

	@Test
	void shouldBeEqual_whenReceiversHaveSameNameAndPhoneNumber() {
		Receiver receiver1 = new Receiver("John Doe", "123-456-7890");
		Receiver receiver2 = new Receiver("John Doe", "123-456-7890");

		assertEquals(receiver1, receiver2);
	}
}
