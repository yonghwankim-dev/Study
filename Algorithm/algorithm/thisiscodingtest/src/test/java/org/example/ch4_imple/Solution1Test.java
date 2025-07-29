package org.example.ch4_imple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Solution1Test {

	@Test
	void test(){
	    // given
		Solution1 solution1 = new Solution1();
		// when
		int[] solution = solution1.solution(5, new String[] {"R", "R", "R", "U", "D", "D"});
		// then
		Assertions.assertArrayEquals(new int[]{3, 4}, solution);
	}

}
