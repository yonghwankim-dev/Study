package BOJ.BOJ_17298;


import org.junit.Test;

class MainTest {

	@Test
	void test() {
		int n = 1000000;
		int[] arr = new int[n];
		for(int i=0;i<n;i++)
		{
			arr[i] = 1;
		}
		System.out.println(Main.solution(arr, n));
	}
	
	
	@Test
	void test2() {	
		int n = 5;
		int[] seq = {9,2,7,8,5};
		
		System.out.println(Main.solution(seq, n));
	}
}
