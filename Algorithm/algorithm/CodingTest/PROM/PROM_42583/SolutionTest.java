package PROM.PROM_42583;


import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

class SolutionTest {

	@Test
	void test1() {
		int answer = new Solution().solution(2, 10, new int[]{7, 4, 5, 6});
		assertEquals(8, answer);
	}
	
	@Test
	void test2() {
		int answer = new Solution().solution(100, 100, new int[] {10});
		assertEquals(101, answer);
	}
	
	@Test
	void test3() {
		int answer = new Solution().solution(100, 100, new int[] {10, 10, 10, 10, 10, 10, 10, 10, 10, 10});
		assertEquals(110, answer);		
	}

}
