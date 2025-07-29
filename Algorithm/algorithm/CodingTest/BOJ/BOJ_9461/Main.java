package BOJ.BOJ_9461;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static long[] dp = new long[101];
	
	public static long solution(int n)
	{
		if(n>=1 && n<=100)
		{
			if(dp[n]!=0)
			{
				return dp[n];
			}
		}
		
		if(n<=3)
		{
			return dp[n] = 1;
		}
		return dp[n] = solution(n-2) + solution(n-3);
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine());
		
		for(int i=0;i<tc;i++)
		{
			int n = Integer.parseInt(br.readLine());
			System.out.println(solution(n));
		}
		
	}
}
