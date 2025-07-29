package org.example.ch4_imple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Solution3Test {

	@Test
	void test(){
	    // given
		String position = "a1";
	    // when
		int solution = new Solution3().solution(position);
		// then
		Assertions.assertEquals(2, solution);
	}
}
