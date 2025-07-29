package PROM.PROM_92342;


import org.junit.Test;

import java.util.Arrays;



class SolutionTest {

	@Test
	void test1() {
		int[] info = {2,1,1,1,0,0,0,0,0,0,0};
		int[] answer = new Solution().solution(5, info);
		System.out.println(Arrays.toString(answer));
	}
	
	@Test
	void test2() {
		int[] info = {1,0,0,0,0,0,0,0,0,0,0};
		int[] answer = new Solution().solution(1, info);
		System.out.println(Arrays.toString(answer));
	}
	
	@Test
	void test3() {
		int[] info = {0,0,1,2,0,1,1,1,1,1,1};
		int[] answer = new Solution().solution(9, info);
		System.out.println(Arrays.toString(answer));
	}
	
	@Test
	void test4() {
		int[] info = {0,0,0,0,0,0,0,0,3,4,3};
		int[] answer = new Solution().solution(10, info);
		System.out.println(Arrays.toString(answer));
	}

}
