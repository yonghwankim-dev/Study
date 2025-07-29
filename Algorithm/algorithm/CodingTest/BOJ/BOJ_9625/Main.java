package BOJ.BOJ_9625;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	static int[] dp;
	
	public static int solution(int n, int initVal)
	{
		if(n==1)
		{
			return initVal;
		}
		if(n==2)
		{
			return 1;
		}
		
		if(dp[n]!=0)
		{
			return dp[n];
		}
		else
		{
			dp[n] = solution(n-1, initVal) + solution(n-2, initVal);
			return dp[n];
		}
	}
		
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		
		dp = new int[1000001];
		int A = solution(n, 0);
		dp = new int[1000001];
		int B = solution(n, 1);
		
		System.out.println(A + " " + B);
	}
}
