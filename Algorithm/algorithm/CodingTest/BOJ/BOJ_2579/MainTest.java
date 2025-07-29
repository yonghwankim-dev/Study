package BOJ.BOJ_2579;


import org.junit.Test;

class MainTest {

	@Test
	void test1() {
		int[] arr = {0,10,20,15,25,10,20};
		int n = arr.length-1;
		Main.dp = new int[n+1];
		System.out.println(Main.solution(n));
	}
	
	@Test
	void test2() {
		int[] arr = new int[301];
		int n = arr.length-1;
		Main.dp = new int[n+1];
		
		for(int i=1;i<arr.length;i++)
		{
			arr[i] = 10000;
		}
		
		System.out.println(Main.solution(n));
	}

}
