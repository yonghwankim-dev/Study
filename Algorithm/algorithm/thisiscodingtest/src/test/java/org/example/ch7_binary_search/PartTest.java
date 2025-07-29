package org.example.ch7_binary_search;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PartTest {

	@Test
	void test(){
	    // given
		Part part = new Part();
		int[] parts = {8, 3, 7, 9, 2};
		int[] targets = {5, 7, 9};
		// when
		String answer = part.solution(parts, targets);
		// then
		Assertions.assertEquals("no yes yes", answer);
	}
}
