package org.example.ch3_gridy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Solution3Test {

	@Test
	void solution(){
	    // given
		Solution3 solution3 = new Solution3();
		// when
		int answer = solution3.solution(17, 4);
		int answer2 = solution3.solution(25, 5);
		int answer3 = solution3.solution(100000, 2);
		// then
		Assertions.assertEquals(3, answer);
		Assertions.assertEquals(2, answer2);
		Assertions.assertEquals(21, answer3);
	}

}
