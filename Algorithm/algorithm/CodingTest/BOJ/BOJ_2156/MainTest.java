package BOJ.BOJ_2156;


import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

class MainTest {

	@Test
	void test() {
		long[] arr = {0,6,10,13,9,8,1};
		long[] dp = new long[arr.length];
		assertEquals(Main.solution(dp,arr, arr.length-1), 33);
	}
	
	@Test
	void test2() {
		long[] arr = {0,100,400,2,1,4,200};
		long[] dp = new long[10001];
		assertEquals(Main.solution(dp,arr, 6), 704);
	}
	
	@Test
	void test3() {
		long[] arr = {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		long[] dp = new long[10001];
		assertEquals(Main.solution(dp,arr, arr.length-1), 14);
	}
	
	@Test
	void test4() {
		long[] arr = new long[10001];
		long[] dp = new long[10001];
		
		for(int i=1;i<=arr.length-1;i++)
		{
			arr[i] = i;
		}
		
		System.out.println(Main.solution(dp,arr, arr.length-1));
	}
	
	@Test
	void test5() {
		long[] arr = {0,6,10,13,9};
		long[] dp = new long[arr.length+1];
		assertEquals(Main.solution(dp,arr, arr.length-1), 28);
	}
}
