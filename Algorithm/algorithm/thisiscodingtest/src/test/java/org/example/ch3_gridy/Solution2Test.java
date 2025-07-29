package org.example.ch3_gridy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Solution2Test {

	@DisplayName("숫자 카드게임을 수행한다")
	@Test
	void solution(){
	    // given
		Solution2 solution2 = new Solution2();
		int[][] cards = new int[][]{{3, 1, 2}, {4, 1, 4}, {2, 2, 2}};
		// when
		int answer = solution2.solution(cards);
		// then
		assertEquals(2, answer);
	}
}
