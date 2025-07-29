package org.example.ch4_imple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Solution4Test {

	@Test
	void test(){
	    // given
		int[][] map = {
			{1, 1, 1, 1},
			{1, 0, 0, 1},
			{1, 1, 0, 1},
			{1, 1, 1, 1}
		};
		// when
		int answer = new Solution4().solution(4, 4, new int[] {1, 1}, 0, map);
		// then
		assertEquals(3, answer);
	}
}
