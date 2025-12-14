package com.nemo.troubleshooting.ch03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class DecoderTest {
	@Test
	void decode() {
		Decoder decoder = new Decoder();
		List<String> encoded = List.of(
			"a1b2c3",
			"a"
		);

		Integer actual = decoder.decode(encoded);

		assertEquals(6, actual);
	}

}
