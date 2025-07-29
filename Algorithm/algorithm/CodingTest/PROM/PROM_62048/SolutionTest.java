package PROM.PROM_62048;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

class SolutionTest {

	@Test
	void test() {
		long answer = new Solution().solution(8, 12);
		assertEquals(80, answer);
	}

}
