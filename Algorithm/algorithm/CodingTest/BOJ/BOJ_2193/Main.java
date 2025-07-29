package BOJ.BOJ_2193;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static long solution(long[] dp, int n)
	{
		if(dp[n]!=0)
		{
			return dp[n];
		}
		
		if(n<=2)
		{
			dp[n] = 1;
		}else {	
			dp[n] = solution(dp, n-1) + solution(dp, n-2);
		}
		return dp[n];
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		long[] dp = new long[n+1];
		System.out.println(solution(dp,n));
	}
}
