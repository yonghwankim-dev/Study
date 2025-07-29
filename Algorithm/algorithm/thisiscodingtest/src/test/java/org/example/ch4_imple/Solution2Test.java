package org.example.ch4_imple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Solution2Test {

	@Test
	void test(){
	    // given
		int n = 5;
	    // when
		int solution = new Solution2().solution(5);
		// then
		Assertions.assertEquals(11475, solution);
	}
}
