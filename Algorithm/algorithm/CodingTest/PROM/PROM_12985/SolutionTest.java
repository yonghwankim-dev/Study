package PROM.PROM_12985;


import org.junit.Assert;
import org.junit.Test;

class SolutionTest {

	@Test
	void test1() {
		Assert.assertEquals(3, new Solution().solution(8, 4, 7));
	}
	
	@Test
	void test2() {
		Assert.assertEquals(3, new Solution().solution(8, 1, 5));
	}
	
	@Test
	void test3() {
		Assert.assertEquals(3, new Solution().solution(8, 2, 8));
	}
	
	@Test
	void test4() {
		Assert.assertEquals(2, new Solution().solution(8, 2, 3));
	}

}
