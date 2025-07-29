package BOJ.BOJ_1149;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class MainTest {
	static int[][] arr;
	static long[][] dp;
	
	@Test
	void test() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		arr = new int[n+1][3];
		dp = new long[n+1][3];
		
		for(int i=1;i<=n;i++)
		{
			int[] rgb = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			arr[i] = rgb;
		}
		Main.arr = arr;
		Main.dp = dp;
		
		System.out.println(Main.solution(n));
	}

}
