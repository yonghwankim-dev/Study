package PROM.PROM_42747;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

class SolutionTest {

	@Test
	void test() {
		int[] citations = {3,0,6,1,5};

		int answer=  new Solution().solution(citations);
		assertEquals(3, answer);
	}

	@Test
	void test2() {
		int[] citations = {31, 66};
		assertEquals(2, new Solution().solution(citations));
	}
}
