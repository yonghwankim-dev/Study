package com.nemo.troubleshooting.ch03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class DecoderTest {
	@Test
	void decode() {
		Decoder decoder = new Decoder();
		List<String> encoded = List.of(
			"ab1c"
		);

		Integer actual = decoder.decode(encoded);

		assertEquals(1, actual);
	}

}
