package CSES.CSES_1083;


import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

class SolutionTest {

	@Test
	void whenMissingNumber9_thenAssert9() {
		long n = 10;
		long[] arr = {1, 2, 4, 6, 3, 7, 8, 10, 5};
		
		assertEquals(9, new Solution().solution1(n, arr));
	}
	
	@Test
	void whenMissingNumber1_thenAssert1() {
		long n = 5;
		long[] arr = {3, 2, 4, 5};
				
		assertEquals(1, new Solution().solution1(n, arr));
	}

	@Test
	void test3() {
		long n = 5;
		long[] arr = {3, 2, 4, 5};
				
		assertEquals(1, new Solution().solution2(n, arr));
	}	
}
